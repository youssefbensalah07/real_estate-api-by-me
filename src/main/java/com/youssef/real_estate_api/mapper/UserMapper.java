package com.youssef.real_estate_api.mapper;

import com.youssef.real_estate_api.domain.User;
import com.youssef.real_estate_api.dto.UserRequestDTO;
import com.youssef.real_estate_api.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role")
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "role", source = "role")
    UserResponseDTO toDTO(User user);
}
