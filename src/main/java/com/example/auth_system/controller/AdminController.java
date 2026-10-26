package com.example.auth_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @GetMapping("/test")
    public Map<String,String> adminTest(){
        return Map.of(
                "message","Admin access granted!"
        );
    }
}
