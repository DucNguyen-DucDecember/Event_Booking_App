package com.example.eba.controller;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.ReminderRequest;
import com.example.eba.dto.ReminderResponse;
import com.example.eba.service.ReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ReminderService reminderService;

    @PutMapping("/reminders")
    public ResponseEntity<ApiResponse<ReminderResponse>> updateReminder(
            @Valid @RequestBody ReminderRequest request) {
        ReminderResponse response = reminderService.updateReminder(request);
        return ResponseEntity.ok(ApiResponse.ok("Reminder settings updated successfully", response));
    }
}