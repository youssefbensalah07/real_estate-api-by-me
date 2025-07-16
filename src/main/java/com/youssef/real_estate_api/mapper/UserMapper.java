package com.youssef.real_estate_api.mapper;

import com.youssef.real_estate_api.domain.User;
import com.youssef.real_estate_api.dto.UserRequestDTO;
import com.youssef.real_estate_api.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO dto);
    UserResponseDTO toDTO(User user);
}
