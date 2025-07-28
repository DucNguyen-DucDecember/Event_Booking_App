package com.example.eba.service;

import com.example.eba.dto.ReminderRequest;
import com.example.eba.dto.ReminderResponse;
import com.example.eba.entity.Reminder;
import com.example.eba.entity.User;
import com.example.eba.repository.ReminderRepository;
import com.example.eba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public ReminderResponse updateReminder(ReminderRequest request) {
        // Validation
        if (request.getEventReminder() == null) {
            throw new IllegalArgumentException("Event reminder must be true or false");
        }

        // Lấy thông tin user từ JWT token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId = userDetails.getUser().getId();

        // Tìm user trong database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Tìm hoặc tạo mới reminder cho user
        Reminder reminder = reminderRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Reminder newReminder = Reminder.builder()
                            .user(user)
                            .eventReminder(true)
                            .build();
                    return reminderRepository.save(newReminder);
                });

        // Cập nhật giá trị eventReminder từ request
        reminder.setEventReminder(request.getEventReminder());
        reminderRepository.save(reminder);

        return ReminderResponse.builder()
                .eventReminder(reminder.isEventReminder())
                .build();
    }
}