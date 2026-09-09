package com.tripify.backend.service;

import com.tripify.backend.dto.AuthResponse;
import com.tripify.backend.dto.LoginRequest;
import com.tripify.backend.dto.LoginResponse;
import com.tripify.backend.dto.RegisterRequest;
import com.tripify.backend.entity.User;
import com.tripify.backend.exception.EmailAlreadyExistsException;
import com.tripify.backend.exception.InvalidCredentialsException;
import com.tripify.backend.repository.UserRepository;
import com.tripify.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered"
            );
        }

        String hashedPassword =
                passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName().trim(),
                email,
                hashedPassword
        );

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String accessToken = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        AuthResponse authResponse = new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return new LoginResponse(
                accessToken,
                "Bearer",
                authResponse
        );
    }
}