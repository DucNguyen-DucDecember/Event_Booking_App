package com.example.Event_Booking_App.controller;

import com.example.Event_Booking_App.dto.ApiResponse;
import com.example.Event_Booking_App.dto.ReminderRequest;
import com.example.Event_Booking_App.dto.ReminderResponse;
import com.example.Event_Booking_App.service.ReminderService;
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