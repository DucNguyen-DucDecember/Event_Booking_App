package com.example.eba.controller;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.EventRequest;
import com.example.eba.dto.EventResponse;
import com.example.eba.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/events")
@Validated
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(
            @Valid @RequestBody EventRequest request
    ) {
        EventResponse data = eventService.createEvent(request);
        ApiResponse<EventResponse> body =
                ApiResponse.ok("Event created successfully", data);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(
            @PathVariable @Positive(message = "Invalid event id") Long id,
            @Valid @RequestBody EventRequest request
    ) {
        EventResponse data = eventService.updateEvent(id, request);
        ApiResponse<EventResponse> body =
                ApiResponse.ok("Event updated successfully", data);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable @Positive(message = "Invalid event id") Long id
    ) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
