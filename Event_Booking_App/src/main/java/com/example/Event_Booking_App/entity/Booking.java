package com.example.Event_Booking_App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private int quantity;
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    private StatusBooking status = StatusBooking.PENDING;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

//    Foreign key
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    @JsonIgnore
    private Payment payment;

    @OneToMany(mappedBy = "booking")
    private List<User> users = new ArrayList<User>();

    @OneToMany(mappedBy = "booking")
    private List<Event> events = new ArrayList<Event>();
}
