package com.youssef.real_estate_api.repository;

import com.youssef.real_estate_api.domain.Address;
import com.youssef.real_estate_api.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepo extends JpaRepository<Address, Long> {
}
