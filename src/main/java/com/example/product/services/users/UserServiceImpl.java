package com.example.product.services.users;

import com.example.product.entities.User;
import com.example.product.models.request.ReqUserDTO;
import com.example.product.models.response.ResUserDTO;
import com.example.product.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResUserDTO createUser(ReqUserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setActive(dto.getActive());
        user.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        user.setUpdatedAt(dto.getUpdatedAt() != null ? dto.getUpdatedAt() : LocalDateTime.now());
        user.setCreatedBy(dto.getCreatedBy());
        user.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(userRepository.save(user));
    }

    @Override
    public ResUserDTO updateUser(Long id, ReqUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setActive(dto.getActive());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(dto.getUpdatedBy());

        return mapToResDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResUserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResDTO)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public List<ResUserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResDTO)
                .collect(Collectors.toList());
    }

    private ResUserDTO mapToResDTO(User user) {
        return new ResUserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getCreatedBy(),
                user.getUpdatedBy());
    }
}