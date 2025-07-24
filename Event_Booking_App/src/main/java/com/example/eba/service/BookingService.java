package com.example.eba.service;

import com.example.eba.dto.BookingRequest;
import com.example.eba.dto.BookingResponse;
import com.example.eba.entity.StatusBooking;
import com.example.eba.entity.StatusPayment;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request, String email);
}
