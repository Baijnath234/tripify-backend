package com.tripify.backend.dto;

import com.tripify.backend.entity.UserRole;

public class AuthResponse {

    private String id;
    private String name;
    private String email;
    private UserRole role;

    public AuthResponse(
            String id,
            String name,
            String email,
            UserRole role
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
}