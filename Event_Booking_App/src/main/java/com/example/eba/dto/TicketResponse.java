package com.example.eba.dto;

import com.example.eba.entity.StatusBooking;
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