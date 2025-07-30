package com.example.eba.controller;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.RegisterRequest;
import com.example.eba.dto.RegisterResponse;
import com.example.eba.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@Validated
public class AdminUserController {

    private final AuthService authService;

    public AdminUserController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Tạo user hoặc admin mới. Role mặc định: ADMIN.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<RegisterResponse>> createAdmin(
            @Valid @RequestBody RegisterRequest req
    ) {
        RegisterResponse data = authService.registerAdmin(req);
        ApiResponse<RegisterResponse> resp = ApiResponse.ok("Admin user created", data);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    /**
     * Có thể thêm endpoint khác để đổi role, disable user…
     */
}