package com.example.Event_Booking_App.exception;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super("Event not found");
    }
}
