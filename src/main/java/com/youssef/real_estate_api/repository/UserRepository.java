package com.youssef.real_estate_api.repository;

import com.youssef.real_estate_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}