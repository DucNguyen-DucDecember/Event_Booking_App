package com.example.eba.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) {
        super("Booking not found: " + message);
    }
}
