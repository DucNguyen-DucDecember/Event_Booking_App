package com.example.eba.service;

import com.example.eba.dto.EventResponse;
import com.example.eba.dto.TicketResponse;
import com.example.eba.entity.Booking;
import com.example.eba.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public List<TicketResponse> getUserTickets() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Long userId = userDetails.getUser().getId();

        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(booking -> TicketResponse.builder()
                        .ticketId(booking.getId())
                        .event(EventResponse.builder()
                                .id(booking.getEvent().getId())
                                .title(booking.getEvent().getTitle())
                                .dateTime(booking.getEvent().getDateTime())
                                .location(booking.getEvent().getLocation())
                                .build())
                        .quantity(booking.getQuantity())
                        .status(booking.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}