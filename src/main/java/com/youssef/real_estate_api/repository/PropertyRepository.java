package com.youssef.real_estate_api.repository;


import com.youssef.real_estate_api.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
