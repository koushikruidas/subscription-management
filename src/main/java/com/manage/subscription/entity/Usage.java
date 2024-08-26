package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data
public class Usage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String billingPeriod;

    @ManyToOne
    @JoinColumn(name = "phase_id")  // Adding JoinColumn for clarity
    private Phase phase;

    @OneToMany(mappedBy = "usage", cascade = CascadeType.ALL)
    private List<Tier> tiers;
}