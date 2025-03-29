package com.maher.bookingapp.controller;

import com.maher.bookingapp.dto.LoginRequest;
import com.maher.bookingapp.dto.UserRequest;
import com.maher.bookingapp.dto.UserResponse;
import com.maher.bookingapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {
        UserResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body is null!");
        }
        String token = userService.loginUser(request);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
