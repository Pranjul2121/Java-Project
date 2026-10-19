
package com.example.auth_system.dto.auth;

import java.time.LocalDateTime;

public record RegisterResponse(
        Long id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
) {}
