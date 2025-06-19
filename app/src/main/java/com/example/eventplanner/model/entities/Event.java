package com.example.eventplanner.model.entities;

import com.example.eventplanner.model.entities.EventType;
import com.example.eventplanner.model.entities.Grade;
import com.example.eventplanner.model.Location;

import java.time.LocalDateTime;
import java.util.Set;

public class Event {
    private Long id;

    private String name;

    private String description;

    private EventType eventType;

    private boolean isPrivate;

    private Integer maxParticipants;

    private Location location;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private double totalBudget;

    private EventOrganizer organizer;

    private Set<Activity> activities;

    private Set<Grade> grades;

    private Set<Comment> comments;


    public Event(Long id, String name, String description, EventType eventType, boolean isPrivate, Integer maxParticipants,
                 Location location, Set<String> images, LocalDateTime startDate, LocalDateTime endDate, EventOrganizer organizer) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.isPrivate = isPrivate;
        this.maxParticipants = maxParticipants;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
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

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public EventOrganizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(EventOrganizer organizer) {
        this.organizer = organizer;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
