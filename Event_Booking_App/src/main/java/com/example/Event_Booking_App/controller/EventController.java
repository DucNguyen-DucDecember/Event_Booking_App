package com.example.Event_Booking_App.controller;

import com.example.Event_Booking_App.dto.ApiResponse;
import com.example.Event_Booking_App.dto.EventResponse;
import com.example.Event_Booking_App.service.EventService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getEvents(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "Page must be a positive integer") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be a positive integer") int size) {
        List<EventResponse> events = eventService.getEvents(type, search, page, size);
        return ResponseEntity.ok(ApiResponse.ok("Events fetched successfully", events));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id) {
        EventResponse event = eventService.getEventById(id);
        return ResponseEntity.ok(ApiResponse.ok("Event detail fetched successfully", event));
    }
}