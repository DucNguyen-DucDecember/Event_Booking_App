package com.example.Event_Booking_App.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Builder.Default
    @Column(name = "event_reminder", nullable = false)
    private boolean eventReminder = true;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

//    Foreign key
    @OneToOne(mappedBy = "reminder")
    private User user;
}
