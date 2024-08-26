package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate billingStartDate;
    private LocalDate nextBillingDate;

    @ManyToOne
    @JoinColumn(name = "plan_id")  // Adding JoinColumn for clarity
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "phase_id")  // Adding JoinColumn for clarity
    private Phase currentPhase;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<Billing> billings;
}
