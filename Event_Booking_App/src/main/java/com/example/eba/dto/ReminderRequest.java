package com.example.eba.dto;

import jakarta.validation.constraints.NotNull;

public class ReminderRequest {

    @NotNull(message = "Must be true or false")
    private Boolean eventReminder;

    public Boolean getEventReminder() {
        return eventReminder;
    }

    public void setEventReminder(Boolean eventReminder) {
        this.eventReminder = eventReminder;
    }
}