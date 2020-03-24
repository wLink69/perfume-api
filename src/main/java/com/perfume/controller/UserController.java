package com.perfume.controller;

import com.perfume.constant.RoleEnum;
import com.perfume.entity.Role;
import com.perfume.repository.RoleRepository;
import com.perfume.repository.UserRepository;
import com.perfume.entity.User;
import com.perfume.sercurity.JwtToken;
import com.perfume.sercurity.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtTokenUtil;

    @PostMapping("/add")
    public ResponseEntity<User> create(@RequestBody User user) throws NoSuchAlgorithmException {
        String username = user.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new ValidationException("Username already existed");
        }

        String password = user.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);

        Role role = roleRepository.findByName(RoleEnum.ROLE_MEMBER.toString());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(encodedPassword);

        userRepository.save(user);

        user.setPassword("");
        return ResponseEntity.ok(user);
    }
}
