package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private int duration;
    private String recurringBillingPeriod;
    private BigDecimal fixedPrice;
    private BigDecimal recurringPrice;

    @ManyToOne
    @JoinColumn(name = "plan_id")  // Adding JoinColumn for clarity
    private Plan plan;

    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL)
    private List<Usage> usages;

    @OneToMany(mappedBy = "currentPhase", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;
}