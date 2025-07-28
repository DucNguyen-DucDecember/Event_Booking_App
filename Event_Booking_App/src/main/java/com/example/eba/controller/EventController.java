package com.example.eba.controller;

import com.example.eba.dto.ApiResponse;
import com.example.eba.dto.EventRequest;
import com.example.eba.dto.EventResponse;
import com.example.eba.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@Validated
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EventResponse>>> getAllEvents(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam Map<String, String> allParams) throws MissingServletRequestParameterException {
        if (!allParams.containsKey("page") || !allParams.containsKey("size")) {
            throw new MissingServletRequestParameterException("page or size", "Integer");
        }
        String type = allParams.get("type");
        String search = allParams.get("search");
        Page<EventResponse> eventsPage = eventService.getAllEvents(type, search, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(eventsPage.getTotalElements()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(ApiResponse.ok("Events fetched successfully", eventsPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(
            @PathVariable @Positive(message = "Invalid event id") Long id) {
        EventResponse data = eventService.getEventById(id);
        return ResponseEntity.ok(ApiResponse.ok("Event fetched successfully", data));
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
        return ResponseEntity.noContent().build();
    }
}