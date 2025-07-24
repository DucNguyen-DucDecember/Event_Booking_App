package com.example.eba.service;

import com.example.eba.dto.EventRequest;
import com.example.eba.dto.EventResponse;

public interface EventService {
    EventResponse createEvent(EventRequest request);
    EventResponse updateEvent(EventRequest request);

    EventResponse updateEvent(Long id, EventRequest request);
    void deleteEvent(Long id);

}
