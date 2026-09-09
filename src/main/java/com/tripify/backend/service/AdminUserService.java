package com.tripify.backend.service;

import com.tripify.backend.dto.AdminUserResponse;
import com.tripify.backend.entity.User;
import com.tripify.backend.entity.UserRole;
import com.tripify.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.tripify.backend.exception.UserNotFoundException;

import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AdminUserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return toResponse(user);
    }

    public AdminUserResponse updateUserRole(
            String id,
            UserRole role
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        user.setRole(role);

        User updatedUser = userRepository.save(user);

        return toResponse(updatedUser);
    }

    private AdminUserResponse toResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}