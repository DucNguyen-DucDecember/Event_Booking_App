package com.example.eba.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderRequest {
    @NotNull(message = "Must be true or false")
    private Boolean eventReminder;
}