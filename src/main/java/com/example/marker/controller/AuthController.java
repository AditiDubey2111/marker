package com.example.marker.controller;

import com.example.marker.dto.LoginRequest;
import com.example.marker.dto.LoginResponse;
import com.example.marker.dto.UserBaseDto;
import com.example.marker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserBaseDto getCurrentUser() {
        // This will be implemented with security context
        return null; 
    }
}
