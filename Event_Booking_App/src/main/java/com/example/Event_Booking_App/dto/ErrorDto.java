package com.example.Event_Booking_App.dto;

public class ErrorDto {
    private String field;
    private String message;

    public ErrorDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // getters / setters
    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
