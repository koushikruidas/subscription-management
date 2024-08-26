package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate billingDate;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "subscription_id")  // Adding JoinColumn for clarity
    private Subscription subscription;
}
