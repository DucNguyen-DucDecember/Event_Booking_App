package com.example.eba.entity;

import java.time.LocalDateTime;

public class EventInfo {
    private long id;
    private String title;
    private LocalDateTime dateTime;
    private String location;

    public EventInfo(long id, String title, LocalDateTime dateTime, String location) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
