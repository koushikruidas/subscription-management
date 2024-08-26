package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String version;

    @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)  // Added mappedBy for bidirectional relationship
    private List<Product> products;
}