package com.example.Event_Booking_App.service;

import com.example.Event_Booking_App.dto.EventRequest;
import com.example.Event_Booking_App.dto.EventResponse;

public interface EventService {
    EventResponse createEvent(EventRequest request);
    EventResponse updateEvent(EventRequest request);

    EventResponse updateEvent(Long id, EventRequest request);
    void deleteEvent(Long id);
}
