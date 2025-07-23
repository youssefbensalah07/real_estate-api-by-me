package com.youssef.real_estate_api.repository;

import com.youssef.real_estate_api.domain.Photo;
import com.youssef.real_estate_api.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
