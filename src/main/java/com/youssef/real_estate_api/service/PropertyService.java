package com.youssef.real_estate_api.service;

import com.youssef.real_estate_api.dto.PropertyRequestDTO;
import com.youssef.real_estate_api.dto.PropertyResponseDTO;

import java.util.List;

public interface PropertyService {
    PropertyResponseDTO create(PropertyRequestDTO dto);
    List<PropertyResponseDTO> getAll();
    List<PropertyResponseDTO> filter(
            String city,
            Boolean promo,
            String type,
            String unit,
            Integer minRooms,
            Integer stars
    );
}
