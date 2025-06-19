package com.example.eventplanner.model.homepage;

import com.example.eventplanner.model.entities.Event;

import java.time.LocalDateTime;

public class EventHomeResponse {
    private Long id;
    private String name;
    private String description;
    private String eventType;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public EventHomeResponse() {
    }

    public EventHomeResponse(Long id, String name, String description, String eventType, String location, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public EventHomeResponse(Event event) {
        this.id=event.getId();
        this.name=event.getName();
        this.description=event.getDescription();
        if(event.getEventType()!=null)
            this.eventType=event.getEventType().getName().toString();
        if(event.getLocation()!=null)
            this.location=event.getLocation().getCity()+","+event.getLocation().getStreet()+" "+event.getLocation().getStreetNumber();
        this.startDate=event.getStartDate();
        this.endDate=event.getEndDate();
    }


}
