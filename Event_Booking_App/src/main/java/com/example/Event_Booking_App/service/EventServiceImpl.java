package com.example.Event_Booking_App.service;

import com.example.Event_Booking_App.dto.EventRequest;
import com.example.Event_Booking_App.dto.EventResponse;
import com.example.Event_Booking_App.entity.Event;
import com.example.Event_Booking_App.exception.EventNotFoundException;
import com.example.Event_Booking_App.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event e = mapToEntity(request);
        Event saved = eventRepository.save(e);
        return mapToResponse(saved);
    }

    @Override
    public EventResponse updateEvent(EventRequest request) {
        return null;
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        // update tất cả các field
        e.setTitle(request.getTitle());
        e.setDateTime(request.getDateTime());
        e.setLocation(request.getLocation());
        e.setPrice(request.getPrice());
        e.setDescription(request.getDescription());
        e.setImageUrl(request.getImageUrl());

        Event updated = eventRepository.save(e);
        return mapToResponse(updated);
    }

    @Override
    public void deleteEvent(Long id) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        eventRepository.delete(e);
    }

    // helper chuyển DTO → Entity
    private Event mapToEntity(EventRequest req) {
        Event e = new Event();
        e.setTitle(req.getTitle());
        e.setDateTime(req.getDateTime());
        e.setLocation(req.getLocation());
        e.setPrice(req.getPrice());
        e.setDescription(req.getDescription());
        e.setImageUrl(req.getImageUrl());
        return e;
    }

    // helper chuyển Entity → DTO
    private EventResponse mapToResponse(Event src) {
        EventResponse resp = new EventResponse();
        resp.setId(src.getId());
        resp.setTitle(src.getTitle());
        resp.setDateTime(src.getDateTime());
        resp.setLocation(src.getLocation());
        resp.setPrice(src.getPrice());
        resp.setDescription(src.getDescription());
        resp.setImageUrl(src.getImageUrl());
        return resp;
    }


}
