package com.example.eba.service;

import com.example.eba.dto.ReminderRequest;
import com.example.eba.dto.ReminderResponse;
import com.example.eba.entity.Reminder;
import com.example.eba.entity.User;
import com.example.eba.repository.ReminderRepository;
import com.example.eba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReminderServiceImpl(ReminderRepository reminderRepository, UserRepository userRepository) {
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReminderResponse updateReminder(ReminderRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reminder reminder = reminderRepository.findByUserId(user.getId())
                .orElse(Reminder.builder()
                        .user(user)
                        .eventReminder(true)
                        .build());
        reminder.setEventReminder(request.getEventReminder());
        Reminder saved = reminderRepository.save(reminder);

        ReminderResponse response = new ReminderResponse();
        response.setEventReminder(saved.isEventReminder());
        return response;
    }
}