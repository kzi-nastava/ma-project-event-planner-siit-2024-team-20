package com.example.eventplanner.model.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceBooking {
    private Long id;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Service service;

    public ServiceBooking(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, Service service) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
