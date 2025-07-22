package com.example.Event_Booking_App.controller;

import com.example.Event_Booking_App.dto.*;
import com.example.Event_Booking_App.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(
            @Valid @RequestBody LoginRequest req) {
        JwtResponse jwt = authService.authenticate(req);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest req) {
        RegisterResponse data = authService.register(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Registration successful", data));
    }
}
