package com.example.eba.repository;

import com.example.eba.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);

    @Query(value = "SELECT e.*, COUNT(b.id) AS booking_count " +
            "FROM events e " +
            "LEFT JOIN bookings b ON e.id = b.event_id " +
            "WHERE (:search IS NULL OR e.title LIKE %:search% OR e.description LIKE %:search%) " +
            "GROUP BY e.id " +
            "ORDER BY booking_count DESC",
            countQuery = "SELECT COUNT(DISTINCT e.id) " +
                    "FROM events e " +
                    "LEFT JOIN bookings b ON e.id = b.event_id " +
                    "WHERE (:search IS NULL OR e.title LIKE %:search% OR e.description LIKE %:search%)",
            nativeQuery = true)
    Page<Event> findPopularEvents(String search, Pageable pageable);

    @Query(value = "SELECT e.* " +
            "FROM events e " +
            "WHERE e.date_time >= NOW() " +
            "AND (:search IS NULL OR e.title LIKE %:search% OR e.description LIKE %:search%) " +
            "ORDER BY e.date_time ASC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM events e " +
                    "WHERE e.date_time >= NOW() " +
                    "AND (:search IS NULL OR e.title LIKE %:search% OR e.description LIKE %:search%)",
            nativeQuery = true)
    Page<Event> findUpcomingEvents(String search, Pageable pageable);
}