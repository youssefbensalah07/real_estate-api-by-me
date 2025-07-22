package com.youssef.real_estate_api.service.impl;

import com.youssef.real_estate_api.domain.User;
import com.youssef.real_estate_api.dto.UserRequestDTO;
import com.youssef.real_estate_api.dto.UserResponseDTO;
import com.youssef.real_estate_api.exception.ResourceNotFoundException;
import com.youssef.real_estate_api.mapper.UserMapper;
import com.youssef.real_estate_api.repository.UserRepository;
import com.youssef.real_estate_api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        log.info("Created user with id: {}", saved.getId());
        return userMapper.toDTO(saved);
    }

    @Override
    public List<UserResponseDTO> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getById(Long id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }



//    @Override
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
//    }

}