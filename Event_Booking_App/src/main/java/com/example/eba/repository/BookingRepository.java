package com.example.eba.repository;

import com.example.eba.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findById(Long id);

    @Query("SELECT b FROM Booking b WHERE b.user.email = :email")
    List<Booking> findByUserEmail(String email);
}