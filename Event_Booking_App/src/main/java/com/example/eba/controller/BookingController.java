package com.example.eba.controller;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.TicketResponse;
import com.example.eba.service.BookingService;
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