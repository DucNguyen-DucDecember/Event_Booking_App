package com.example.eba.service;

import com.example.eba.dto.EventRequest;
import com.example.eba.dto.EventResponse;
import com.example.eba.entity.Event;
import com.example.eba.exception.EventNotFoundException;
import com.example.eba.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private EventRequest request;
    private Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new EventRequest();
        request.setTitle("Tech Conference");
        request.setDateTime(LocalDateTime.now().plusDays(1));
        request.setLocation("New York");
        request.setPrice(BigDecimal.valueOf(199.99));
        request.setDescription("A tech event");
        request.setImageUrl("https://example.com/image.jpg");

        event = new Event();
        event.setId(1L);
        event.setTitle("Tech Conference");
        event.setDateTime(request.getDateTime());
        event.setLocation(request.getLocation());
        event.setPrice(request.getPrice());
        event.setDescription(request.getDescription());
        event.setImageUrl(request.getImageUrl());
    }

    @Test
    void testCreateEvent_Success() {
        // Arrange
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Act
        EventResponse response = eventService.createEvent(request);

        // Assert
        assertNotNull(response);
        assertEquals("Tech Conference", response.getTitle());
        assertEquals(BigDecimal.valueOf(199.99), response.getPrice());

        // Verify that save was called once
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testUpdateEvent_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        request.setTitle("Updated Event");

        // Act
        EventResponse response = eventService.updateEvent(1L, request);

        // Assert
        assertEquals("Updated Event", response.getTitle());
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testUpdateEvent_NotFound() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(1L, request));
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    void testDeleteEvent_Success() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        // Act
        eventService.deleteEvent(1L);

        // Assert
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    void testDeleteEvent_NotFound() {
        // Arrange
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EventNotFoundException.class, () -> eventService.deleteEvent(1L));
        verify(eventRepository, never()).delete(any(Event.class));
    }
}
