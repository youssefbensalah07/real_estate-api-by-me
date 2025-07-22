package com.youssef.real_estate_api.repository;

import com.youssef.real_estate_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
}