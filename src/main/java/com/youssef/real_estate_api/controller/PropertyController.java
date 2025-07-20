package com.youssef.real_estate_api.controller;

import com.youssef.real_estate_api.dto.PropertyRequestDTO;
import com.youssef.real_estate_api.dto.PropertyResponseDTO;
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

    @PostMapping
    public ResponseEntity<PropertyResponseDTO> create(@Valid @RequestBody PropertyRequestDTO dto) {
        return ResponseEntity.ok(propertyService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PropertyResponseDTO>> getAll() {
        return ResponseEntity.ok(propertyService.getAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PropertyResponseDTO>> filter(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean promo,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) Integer minRooms,
            @RequestParam(required = false) Integer stars
    ) {
        return ResponseEntity.ok(propertyService.filter(city, promo, type, unit, minRooms, stars));
    }
}
