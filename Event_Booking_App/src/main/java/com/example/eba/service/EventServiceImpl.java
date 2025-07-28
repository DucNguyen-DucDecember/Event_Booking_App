package com.example.eba.service;

import com.example.eba.dto.EventRequest;
import com.example.eba.dto.EventResponse;
import com.example.eba.entity.Event;
import com.example.eba.exception.EventNotFoundException;
import com.example.eba.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Page<EventResponse> getAllEvents(String type, String search, int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("Page must be a positive integer");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Size must be a positive integer");
        }
        if (type != null && !Arrays.asList("popular", "upcoming").contains(type)) {
            throw new IllegalArgumentException("Invalid type value");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Event> eventPage;

        if ("popular".equals(type)) {
            eventPage = eventRepository.findPopularEvents(search, pageable);
        } else if ("upcoming".equals(type)) {
            eventPage = eventRepository.findUpcomingEvents(search, pageable);
        } else {
            eventPage = eventRepository.findAll(pageable);
        }

        return eventPage.map(this::mapToResponse);
    }

    @Override
    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return mapToResponse(event);
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