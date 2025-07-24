package com.example.eba.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {

    @NotNull(message = "Event ID is required.")
    @Min(value=1, message = "Event ID must greater than 0.")
    private Long eventId;

    @NotNull(message = "Quantity is required.")
    @Min(value=1, message = "Quantity must greater than or equal 1.")
    private Integer quantity;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
