package com.youssef.real_estate_api.repository;


import com.youssef.real_estate_api.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> customFilter(String city, Boolean promo, String type, String unit, Integer minRooms, Integer stars);
}

