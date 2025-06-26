package com.example.eventplanner.model.eventPage;

import com.example.eventplanner.model.eventCreation.AgendaCreationRequest;

import java.util.List;

public class EventDisplayResponse {
    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String address;
    private CreatorViewResponse organizer;
    private boolean isPrivate;
    private int guests;
    private List<AgendaResponse> agenda;

    public EventDisplayResponse() {}

    public EventDisplayResponse(Long id, String name, String description, String startDate, String endDate,
                                String startTime, String endTime, String address, CreatorViewResponse organizer,
                                boolean isPrivate, int guests, List<AgendaResponse> agenda) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.address = address;
        this.organizer = organizer;
        this.isPrivate = isPrivate;
        this.guests = guests;
        this.agenda = agenda;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CreatorViewResponse getOrganizer() {
        return organizer;
    }

    public void setOrganizer(CreatorViewResponse organizer) {
        this.organizer = organizer;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public List<AgendaResponse> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<AgendaResponse> agenda) {
        this.agenda = agenda;
    }
}
