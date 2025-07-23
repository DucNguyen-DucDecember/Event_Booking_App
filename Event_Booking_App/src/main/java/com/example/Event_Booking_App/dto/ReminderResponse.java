package com.example.Event_Booking_App.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderResponse {
    private Boolean eventReminder;
}