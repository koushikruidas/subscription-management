package com.manage.subscription.repository;

import com.manage.subscription.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {
    List<Tier> findByUsageId(Long usageId);
}