
package com.example.auth_system.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardController {

    @GetMapping("/api/dashboard")
    public Map<String, String> dashboard(
            Authentication authentication) {

        return Map.of(
                "message", "Welcome to your dashboard!",
                "email", authentication.getName()
        );
    }
}
