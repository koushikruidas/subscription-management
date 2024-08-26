package com.manage.subscription.repository;

import com.manage.subscription.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageRepository extends JpaRepository<Usage, Long> {
    List<Usage> findByPhaseId(Long phaseId);
}