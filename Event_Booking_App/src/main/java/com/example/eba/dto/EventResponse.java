package com.example.eba.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private String location;
    private BigDecimal price;
    private String description;
    private String imageUrl;

    // --- getters & setters ---
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public LocalDateTime getDateTime() { return dateTime; }
//    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
//
//    public String getLocation() { return location; }
//    public void setLocation(String location) { this.location = location; }
//
//    public BigDecimal getPrice() { return price; }
//    public void setPrice(BigDecimal price) { this.price = price; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public String getImageUrl() { return imageUrl; }
//    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
