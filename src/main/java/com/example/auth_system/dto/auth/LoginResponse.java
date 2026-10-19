
package com.example.auth_system.dto.auth;

public record LoginResponse(
        Long id,
        String name,
        String email,
        String role,
        String accessToken,
        String tokenType,
        long expiresIn
) {}
