package com.tripify.backend.dto;

public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private AuthResponse user;

    public LoginResponse(
            String accessToken,
            String tokenType,
            AuthResponse user
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AuthResponse getUser() {
        return user;
    }
}