package com.example.Event_Booking_App.service;

import com.example.Event_Booking_App.dto.ReminderRequest;
import com.example.Event_Booking_App.dto.ReminderResponse;
import com.example.Event_Booking_App.entity.Reminder;
import com.example.Event_Booking_App.entity.User;
import com.example.Event_Booking_App.repository.ReminderRepository;
import com.example.Event_Booking_App.repository.UserRepository;
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
                            .eventReminder(true) // Giá trị mặc định từ @Builder.Default
                            .build();
                    return reminderRepository.save(newReminder);
                });

        // Cập nhật giá trị eventReminder từ request
        reminder.setEventReminder(request.getEventReminder());
        reminderRepository.save(reminder);

        // Trả về response với giá trị eventReminder đã cập nhật
        return ReminderResponse.builder()
                .eventReminder(reminder.isEventReminder()) // Sửa thành isEventReminder
                .build();
    }
}