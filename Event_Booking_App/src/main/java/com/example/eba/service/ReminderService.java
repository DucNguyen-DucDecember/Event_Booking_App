package com.example.eba.service;

import com.example.eba.dto.ReminderRequest;
import com.example.eba.dto.ReminderResponse;

public interface ReminderService {
    ReminderResponse updateReminder(ReminderRequest request, String email);
}