package com.youssef.real_estate_api.controller;

import com.youssef.real_estate_api.domain.Property;
import com.youssef.real_estate_api.domain.User;
import com.youssef.real_estate_api.dto.PropertyRequestDTO;
import com.youssef.real_estate_api.dto.PropertyResponseDTO;
import com.youssef.real_estate_api.mapper.PropertyMapper;
import com.youssef.real_estate_api.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final PropertyMapper propertyMapper;

    @PostMapping
    public ResponseEntity<PropertyResponseDTO> create(@Valid @RequestBody PropertyRequestDTO dto) {
        PropertyResponseDTO created = propertyService.create(dto);
        return ResponseEntity.ok(created);
    }
//    @PostMapping
//    public ResponseEntity<PropertyResponseDTO> createProperty(@RequestBody PropertyRequestDTO dto) {
//        User user = propertyService.getCurrentUser();
//        Property property = propertyService.addProperty(dto, user);
//        return ResponseEntity.ok(propertyMapper.toDTO(property));
//    }

    @GetMapping
    public ResponseEntity<List<PropertyResponseDTO>> getAll() {
        List<PropertyResponseDTO> properties = propertyService.getAll();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/filter")
    public List<Property> filterProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean promo,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) Integer minRooms,
            @RequestParam(required = false) Integer stars) {

        return propertyService.customFilter(city, promo, type, unit, minRooms, stars);
    }

}

