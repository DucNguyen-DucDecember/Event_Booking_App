package com.example.eba.dto;

import com.example.eba.entity.Event;
import com.example.eba.entity.StatusBooking;

public class TicketResponse {
    private Long ticketId;
    private Event event;
    private Integer quantity;
    private StatusBooking status;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public StatusBooking getStatus() {
        return status;
    }

    public void setStatus(StatusBooking status) {
        this.status = status;
    }
}