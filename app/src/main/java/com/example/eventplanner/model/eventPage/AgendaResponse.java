package com.example.eventplanner.model.eventPage;

import java.time.LocalTime;
import java.util.List;

public class AgendaResponse {
    private String name;
    private String description;
    private List<Integer> from;
    private List<Integer> to;
    private String location;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public LocalTime getFromTime() {
        if (from != null && from.size() == 2) {
            return LocalTime.of(from.get(0), from.get(1));
        }
        return null;
    }

    public LocalTime getToTime() {
        if (to != null && to.size() == 2) {
            return LocalTime.of(to.get(0), to.get(1));
        }
        return null;
    }
}
