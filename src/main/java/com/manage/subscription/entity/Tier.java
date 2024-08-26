package com.manage.subscription.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Entity
@Data
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lowerBound;
    private int upperBound;
    private BigDecimal fixedPrice;
    private BigDecimal recurringPrice;

    @ManyToOne
    @JoinColumn(name = "usage_id")  // Adding JoinColumn for clarity
    private Usage usage;
}