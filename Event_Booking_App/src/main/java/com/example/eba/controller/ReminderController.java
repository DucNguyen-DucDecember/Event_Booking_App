package com.example.eba.controller;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.ReminderRequest;
import com.example.eba.dto.ReminderResponse;
import com.example.eba.service.ReminderService;
import com.example.eba.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/reminders")
@Validated
public class ReminderController {

    private final ReminderService reminderService;
    @Autowired private JwtUtils jwtUtils;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ReminderResponse>> updateReminder(
            @Valid @RequestBody ReminderRequest request,
            HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        String email = jwtUtils.getUsernameFromToken(token);
        ReminderResponse response = reminderService.updateReminder(request, email);
        return ResponseEntity.ok(ApiResponse.ok("Reminder settings updated successfully", response));
    }
}