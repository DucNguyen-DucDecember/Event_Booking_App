package com.example.Event_Booking_App.repository;

import com.example.Event_Booking_App.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE " +
            "(:type IS NULL OR " +
            "(:type = 'popular' AND e.price > 50) OR " +
            "(:type = 'upcoming' AND e.dateTime > CURRENT_TIMESTAMP) OR " +
            "(:type = 'nearby' AND e.location LIKE %:location%)) AND " +
            "(:search IS NULL OR e.title LIKE %:search% OR e.description LIKE %:search%)")
    Page<Event> findEventsByTypeAndSearch(String type, String search, String location, Pageable pageable);

    Optional<Event> findById(Long id);
}