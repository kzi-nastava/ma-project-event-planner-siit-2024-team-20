package com.example.eventplanner.model.entities;

import java.time.LocalTime;

public class Activity {
    private Long id;

    private String name;

    private String description;

    private String place;

    private LocalTime startTime;

    private LocalTime endTime;

    public Activity(Long id, String name, String description, String place, LocalTime startTime, LocalTime endTime) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
