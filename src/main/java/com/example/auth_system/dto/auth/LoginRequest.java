
package com.example.auth_system.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email")
        String email,

        @NotBlank(message = "Password is required")
        @Size(max = 72, message = "Invalid password")
        String password

) {}
