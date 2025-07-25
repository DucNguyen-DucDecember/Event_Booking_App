package com.example.eba.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private StatusPayment status = StatusPayment.PAID;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

//    Foreign key
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    @JsonIgnore
    private Booking booking;
}
