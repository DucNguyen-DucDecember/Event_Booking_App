package com.example.eba.service;

import com.example.eba.dto.TicketResponse;
import com.example.eba.entity.Booking;
import com.example.eba.repository.BookingRepository;
import com.example.eba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicketServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TicketResponse> getUserTickets(String email) {
        return bookingRepository.findByUserEmail(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TicketResponse mapToResponse(Booking booking) {
        TicketResponse response = new TicketResponse();
        response.setTicketId(booking.getId());
        response.setEvent(booking.getEvent());
        response.setQuantity(booking.getQuantity());
        response.setStatus(booking.getStatus());
        return response;
    }
}