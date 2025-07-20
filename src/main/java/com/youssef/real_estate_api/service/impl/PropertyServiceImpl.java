package com.youssef.real_estate_api.service.impl;

import com.youssef.real_estate_api.domain.Property;
import com.youssef.real_estate_api.domain.User;
import com.youssef.real_estate_api.dto.PropertyRequestDTO;
import com.youssef.real_estate_api.dto.PropertyResponseDTO;
import com.youssef.real_estate_api.exception.ResourceNotFoundException;
import com.youssef.real_estate_api.mapper.PropertyMapper;
import com.youssef.real_estate_api.repository.PropertyRepository;
import com.youssef.real_estate_api.repository.UserRepository;
import com.youssef.real_estate_api.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyMapper propertyMapper;



    @Override
    public PropertyResponseDTO create(PropertyRequestDTO dto) {
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Property property = propertyMapper.toEntity(dto);
        property.setOwner(owner);
        Property saved = propertyRepository.save(property);

        log.info("Created property with ID: {}", saved.getId());
        return propertyMapper.toDTO(saved);
    }

    @Override
    public List<PropertyResponseDTO> getAll() {
        return propertyRepository.findAll().stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponseDTO> filter(String city, Boolean promo, String type, String unit, Integer minRooms, Integer stars) {
        return propertyRepository.findAll().stream()
                .filter(p -> city == null || p.getCity().equalsIgnoreCase(city))
                .filter(p -> promo == null || p.isPromo() == promo)
                .filter(p -> type == null || p.getType().name().equalsIgnoreCase(type))
                .filter(p -> unit == null || p.getPriceUnit().name().equalsIgnoreCase(unit))
                .filter(p -> minRooms == null || p.getRooms() >= minRooms)
                .filter(p -> stars == null || (p.getStars() != null && p.getStars() >= stars))
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }
}