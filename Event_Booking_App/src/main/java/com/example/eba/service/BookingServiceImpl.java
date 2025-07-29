package com.example.eba.service;

import com.example.eba.dto.BookingRequest;
import com.example.eba.dto.BookingResponse;
import com.example.eba.entity.*;
import com.example.eba.exception.EventNotFoundException;
import com.example.eba.repository.BookingRepository;
import com.example.eba.repository.EventRepository;
import com.example.eba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }


    @Override
    public BookingResponse createBooking(BookingRequest request, String email) {
        User u = userRepository.findByEmail(email).get();
        Booking booking = mapToEntity(request, u);
        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponse(savedBooking);
    }

    private Booking mapToEntity(BookingRequest req, User u) {
        Event e = eventRepository.findById(req.getEventId())
                .orElseThrow(() -> new EventNotFoundException(req.getEventId()));

        Booking b = new Booking();
        b.setQuantity(req.getQuantity());
        b.setEvent(e);
        b.setTotalPrice(e.getPrice().multiply(BigDecimal.valueOf(req.getQuantity())));
        b.setUser(u);
        return b;
    }

    private BookingResponse mapToResponse(Booking src) {
        BookingResponse resp = new BookingResponse();
        resp.setBookingId(src.getId());
        Event event = src.getEvent();
        EventInfo eventInfo = new EventInfo(event.getId(),event.getTitle(),event.getDateTime(),event.getLocation());
        resp.setEvent(eventInfo);
        resp.setQuantity(src.getQuantity());
        resp.setTotalPrice(src.getTotalPrice());
        return resp;
    }
}
