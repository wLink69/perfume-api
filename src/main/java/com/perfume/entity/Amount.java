package com.perfume.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
@Entity
public class Amount extends BaseEntity {
    public String name;
    public String description;

    @OneToMany(mappedBy = "amount")
    public List<Product> products;
}
