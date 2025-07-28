package com.example.eba.service;

import com.example.eba.dto.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> getUserTickets(String email);
}