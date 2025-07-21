package com.youssef.real_estate_api.service;

import com.youssef.real_estate_api.domain.User;
import com.youssef.real_estate_api.dto.UserRequestDTO;
import com.youssef.real_estate_api.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO create(UserRequestDTO dto);

    List<UserResponseDTO> getAll();

    UserResponseDTO getById(Long id);

    void delete(Long id);

    User findByEmail(String email);
}