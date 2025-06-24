package com.example.eventplanner.model.eventCreation;

import java.time.LocalTime;

public class AgendaCreationRequest { //one segment
    private String name;
    private String description;
    private LocalTime from;
    private LocalTime to;
    private String location;

    public AgendaCreationRequest(String name, String description, LocalTime from, LocalTime to, String location) {
        this.name = name;
        this.description = description;
        this.from = from;
        this.to = to;
        this.location = location;
    }

    public AgendaCreationRequest() {
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

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
