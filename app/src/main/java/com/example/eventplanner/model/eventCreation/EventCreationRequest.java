package com.example.eventplanner.model.eventCreation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventCreationRequest {
    private String name;
    private String description;
    private String city;
    private String address;
    private String num;
    private int numOfGuests;
    private Boolean isPrivate;
    private List<String> guestEmails;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Long eventType;
    private double longitude;
    private double latitude;
    private Long creator;
    private List<AgendaCreationRequest> agenda;

    public EventCreationRequest() {}

    public EventCreationRequest(String name, String description, String city, String address, String num,
                                int numOfGuests, Boolean isPrivate, List<String> guestEmails,
                                LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime,
                                Long eventType, double longitude, double latitude, Long creator,
                                List<AgendaCreationRequest> agenda) {
        this.name = name;
        this.description = description;
        this.city = city;
        this.address = address;
        this.num = num;
        this.numOfGuests = numOfGuests;
        this.isPrivate = isPrivate;
        this.guestEmails = guestEmails;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.eventType = eventType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.creator = creator;
        this.agenda = agenda;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getNumOfGuests() {
        return numOfGuests;
    }

    public void setNumOfGuests(int numOfGuests) {
        this.numOfGuests = numOfGuests;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<String> getGuestEmails() {
        return guestEmails;
    }

    public void setGuestEmails(List<String> guestEmails) {
        this.guestEmails = guestEmails;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Long getEventType() {
        return eventType;
    }

    public void setEventType(Long eventType) {
        this.eventType = eventType;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public List<AgendaCreationRequest> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<AgendaCreationRequest> agenda) {
        this.agenda = agenda;
    }
}
