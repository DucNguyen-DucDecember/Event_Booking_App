package com.example.eba.service;

import com.example.eba.dto.BookingRequest;
import com.example.eba.dto.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String email);
}
