package com.example.eba.controller;

import com.example.eba.dto.*;
import com.example.eba.service.BookingService;
import com.example.eba.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController {
    private final BookingService bookingService;
    @Autowired private JwtUtils jwtUtils;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request,
            HttpServletRequest httpRequest
    ) {
        String token = httpRequest.getHeader("Authorization").substring(7);

        System.out.println(token);
        jwtUtils.getUsernameFromToken(token);
        String email = jwtUtils.getUsernameFromToken(token);
        BookingResponse data = bookingService.createBooking(request, email);
        ApiResponse<BookingResponse> body =
                ApiResponse.ok("Booking created successfully", data);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
}
