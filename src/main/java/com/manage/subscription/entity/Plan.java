package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String recurringBillingPeriod;
    private int billingCycleDay;
    @ManyToOne
    @JoinColumn(name = "product_id")  // Adding JoinColumn for clarity
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "plan_price_list",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "price_list_id")
    )
    private List<PriceList> priceLists;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Phase> phases;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

}