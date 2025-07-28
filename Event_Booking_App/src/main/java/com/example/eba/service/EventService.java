package com.example.eba.service;

import com.example.eba.dto.EventRequest;
import com.example.eba.dto.EventResponse;
import org.springframework.data.domain.Page;

public interface EventService {
    EventResponse createEvent(EventRequest request);
    EventResponse updateEvent(EventRequest request);
    EventResponse updateEvent(Long id, EventRequest request);
    void deleteEvent(Long id);
    Page<EventResponse> getAllEvents(String type, String search, int page, int size);
    EventResponse getEventById(Long id);
}