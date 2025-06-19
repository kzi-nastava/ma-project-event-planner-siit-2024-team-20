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
}
