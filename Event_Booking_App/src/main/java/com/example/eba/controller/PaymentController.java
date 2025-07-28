package com.example.eba.controller;

import com.example.eba.dto.*;
import com.example.eba.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@Validated
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> makePayment(
            @Valid @RequestBody PaymentRequest request
    ) {
        PaymentResponse data = paymentService.makePayment(request);
        ApiResponse<PaymentResponse> body =
                ApiResponse.ok("Payment created successfully", data);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
}
