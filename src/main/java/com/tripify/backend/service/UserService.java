package com.tripify.backend.service;

import com.tripify.backend.dto.UserResponse;
import com.tripify.backend.entity.User;
import com.tripify.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getCurrentUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}