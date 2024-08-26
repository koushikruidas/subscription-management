package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String catalogVersion;
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "catalog_id")  // Adding JoinColumn for clarity
    private Catalog catalog;

    @OneToMany(mappedBy = "product")
    private List<Plan> plans;
}