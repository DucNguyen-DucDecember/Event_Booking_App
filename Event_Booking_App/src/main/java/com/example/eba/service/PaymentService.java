package com.example.eba.service;

import com.example.eba.dto.PaymentRequest;
import com.example.eba.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest paymentRequest);


}
