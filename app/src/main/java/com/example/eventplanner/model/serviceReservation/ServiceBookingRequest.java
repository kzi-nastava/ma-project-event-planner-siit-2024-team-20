package com.example.eventplanner.model.serviceReservation;

import com.example.eventplanner.model.entities.ServiceBooking;
import com.example.eventplanner.model.productDetails.ServiceDetailsResponse;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServiceBookingRequest {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ServiceDetailsResponse service;
    private Long eventId;
    public ServiceBookingRequest(ServiceBooking booking) {
        this.date=booking.getDate();
        this.startTime=booking.getStartTime();
        this.endTime=booking.getEndTime();
        if(booking.getService()!=null)this.service=new ServiceDetailsResponse(booking.getService());
    }
    public ServiceBookingRequest(LocalDate date,LocalTime start,LocalTime end, ServiceDetailsResponse service,Long eventId) {
        this.date=date;
        this.startTime = start;
        this.endTime = end;
        this.service=service;
        this.eventId=eventId;
    }
    public ServiceBookingRequest(Long id,LocalDate date, LocalTime startTime, LocalTime endTime, ServiceDetailsResponse service) {
        super();
        this.id = id;
        this.date=date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.service = service;
    }

    public ServiceBookingRequest() {

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

    public ServiceDetailsResponse getService() {
        return service;
    }

    public void setService(ServiceDetailsResponse service) {
        this.service = service;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
