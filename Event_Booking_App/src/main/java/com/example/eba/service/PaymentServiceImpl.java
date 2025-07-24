package com.example.eba.service;

import com.example.eba.dto.PaymentRequest;
import com.example.eba.dto.PaymentResponse;
import com.example.eba.entity.*;
import com.example.eba.exception.BookingNotFoundException;
import com.example.eba.repository.BookingRepository;
import com.example.eba.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public PaymentResponse makePayment (PaymentRequest req){
        Payment payment = mapToEntity(req);
        Payment savedPayment = paymentRepository.save(payment);
        updateStatus(req.getBookingId(), StatusBooking.PAID);
        return mapToResponse(savedPayment);
    }

    private void updateStatus(Long bookingId, StatusBooking statusBooking) {
        Booking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        b.setStatus(statusBooking);
        bookingRepository.save(b);
    }

    private Payment mapToEntity(PaymentRequest req) {
        Booking b = bookingRepository.findById(req.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        Payment p = new Payment();
        p.setAmount(b.getTotalPrice());
        p.setStatus(StatusPayment.PAID);
        p.setBooking(b);
        return p;
    }

    private PaymentResponse mapToResponse(Payment src) {
        PaymentResponse resp = new PaymentResponse();
        resp.setPaymentId(src.getId());
        resp.setBookingId(src.getBooking().getId());
        resp.setAmount(src.getAmount());
        resp.setStatus(src.getStatus());
        return resp;
    }
}
