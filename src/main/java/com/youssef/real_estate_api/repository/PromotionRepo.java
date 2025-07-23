package com.youssef.real_estate_api.repository;

import com.youssef.real_estate_api.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepo extends JpaRepository<Promotion, Long> {
    // Additional query methods can be defined here if needed
}
