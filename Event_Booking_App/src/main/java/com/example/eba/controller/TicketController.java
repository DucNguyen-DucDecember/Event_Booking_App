package com.example.eba.controller;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.TicketResponse;
import com.example.eba.service.TicketService;
import com.example.eba.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class TicketController {

    private final TicketService ticketService;
    @Autowired private JwtUtils jwtUtils;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getUserTickets(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        String email = jwtUtils.getUsernameFromToken(token);
        List<TicketResponse> tickets = ticketService.getUserTickets(email);
        return ResponseEntity.ok(ApiResponse.ok("Tickets fetched successfully", tickets));
    }
}