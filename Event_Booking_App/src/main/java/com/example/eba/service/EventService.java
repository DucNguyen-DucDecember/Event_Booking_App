package com.example.eba.service;

import com.example.eba.dto.EventResponse;
import com.example.eba.entity.Event;
import com.example.eba.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventResponse> getEvents(String type, String search, int page, int size) {
        // Validation
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

        // Chọn repository method dựa trên type
        if ("popular".equals(type)) {
            eventPage = eventRepository.findPopularEvents(type, search, pageable);
        } else if ("upcoming".equals(type)) {
            eventPage = eventRepository.findUpcomingEvents(type, search, pageable);
        } else {
            // Trường hợp không có type, trả về tất cả sự kiện
            eventPage = eventRepository.findAll(pageable);
        }

        return eventPage.getContent().stream()
                .map(event -> EventResponse.builder()
                        .id(event.getId())
                        .title(event.getTitle())
                        .dateTime(event.getDateTime())
                        .location(event.getLocation())
                        .price(event.getPrice())
                        .description(event.getDescription())
                        .imageUrl(event.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public EventResponse getEventById(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Event ID must be a positive integer");
        }
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .dateTime(event.getDateTime())
                .location(event.getLocation())
                .price(event.getPrice())
                .description(event.getDescription())
                .imageUrl(event.getImageUrl())
                .build();
    }
}