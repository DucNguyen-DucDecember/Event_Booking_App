package com.example.eba.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.YearMonth;

public class PaymentRequest {

    @NotNull (message = "Booking ID is required.")
    private Long bookingId;

    @NotNull(message = "Card number is required.")
    @Pattern(regexp = "\\d{16}", message = "Invalid card number")

    private String cardNumber;

    @NotNull(message = "Expiry date is required.")
    @Future(message = "Invalid expiry format")
    @JsonFormat(pattern = "MM/yy")
    private YearMonth expiry;

    @NotNull(message = "CVV is required.")
    @Pattern(regexp = "\\d{3,4}", message = "Invalid CVV")
    private String cvv;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (cardNumber != null){
            this.cardNumber = cardNumber.replaceAll("\\s", "");
        }
        else {
            this.cardNumber = null;
        }
    }

    public YearMonth getExpiry() {
        return expiry;
    }

    public void setExpiry(YearMonth expiry) {
        this.expiry = expiry;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
