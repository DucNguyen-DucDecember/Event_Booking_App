package com.example.eba.controller;

import com.example.eba.dto.EventRequest;
import com.example.eba.dto.EventResponse;
import com.example.eba.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    private EventRequest request;
    private EventResponse eventResponse;

    @BeforeEach
    void initData(){
        request = EventRequest.builder()
                .title("Spring Boot Workshop")
                .dateTime(LocalDateTime.parse("2025-09-01T14:00:00"))
                .location("HCM")
                .price(new BigDecimal("150.00"))
                .description("description")
                .imageUrl("https://cdn.example.com/images/spring_workshop.png")
                .build();

        eventResponse = EventResponse.builder()
                .id(Long.parseLong("1"))
                .title("Spring Boot Workshop")
                .dateTime(LocalDateTime.parse("2025-09-01T14:00:00"))
                .location("HCM")
                .price(new BigDecimal("150.00"))
                .description("description")
                .imageUrl("https://cdn.example.com/images/spring_workshop.png")
                .build();
    }

    @Test
    void createEvent_Success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(eventService.createEvent(ArgumentMatchers.any()))
                .thenReturn(eventResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success").value(true));

    }
}
