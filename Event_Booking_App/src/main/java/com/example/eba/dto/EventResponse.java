package com.example.eba.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private LocalDateTime dateTime;
    private String location;
    private BigDecimal price;
    private String description;
    private String imageUrl;
}