package com.example.Event_Booking_App.controller;

import com.example.Event_Booking_App.dto.ApiResponse;
import com.example.Event_Booking_App.dto.TicketResponse;
import com.example.Event_Booking_App.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getUserTickets() {
        List<TicketResponse> tickets = bookingService.getUserTickets();
        return ResponseEntity.ok(ApiResponse.ok("Tickets fetched successfully", tickets));
    }
}