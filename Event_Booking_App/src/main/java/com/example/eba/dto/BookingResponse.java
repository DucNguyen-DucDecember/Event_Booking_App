package com.example.eba.dto;

import com.example.eba.entity.Event;
import com.example.eba.entity.EventInfo;

import java.math.BigDecimal;

public class BookingResponse {
    private long bookingId;
    private EventInfo event;
    private Integer quantity;
    private BigDecimal totalPrice;

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public EventInfo getEvent() {
        return event;
    }

    public void setEvent(EventInfo event) {
        this.event = event;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
