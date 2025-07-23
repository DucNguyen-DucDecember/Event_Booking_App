package com.example.Event_Booking_App.service;

import com.example.Event_Booking_App.dto.EventResponse;
import com.example.Event_Booking_App.entity.Event;
import com.example.Event_Booking_App.repository.EventRepository;
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
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be positive integers");
        }
        if (type != null && !Arrays.asList("popular", "upcoming", "nearby").contains(type)) {
            throw new IllegalArgumentException("Invalid type value");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        // Giả định location là null nếu không có thông tin vị trí người dùng
        Page<Event> eventPage = eventRepository.findEventsByTypeAndSearch(type, search, null, pageable);

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

//up date 23/7/2025
//Thêm validation cho type (chỉ chấp nhận popular, upcoming, nearby).
//Gọi findEventsByTypeAndSearch từ EventRepository với location=null (có thể mở rộng nếu cần thêm logic vị trí).