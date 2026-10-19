
package com.example.auth_system.controller;

import com.example.auth_system.dto.auth.RegisterRequest;
import com.example.auth_system.dto.auth.RegisterResponse;
import com.example.auth_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.auth_system.dto.auth.LoginRequest;
import com.example.auth_system.dto.auth.LoginResponse;
import com.example.auth_system.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    public AuthController(UserService userService,
                          AuthService authService) {
        this.userService = userService;
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

}
