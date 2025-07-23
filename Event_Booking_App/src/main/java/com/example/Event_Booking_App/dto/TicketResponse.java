package com.example.Event_Booking_App.dto;

import com.example.Event_Booking_App.entity.StatusBooking;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long ticketId;
    private EventResponse event;
    private int quantity;
    private StatusBooking status;
}