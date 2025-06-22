package com.example.eventplanner.model.entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.os.Parcel;

import com.example.eventplanner.model.enumeration.StatusType;

import java.util.HashSet;
import java.util.Set;

public class Service extends Product {
    private String specificity;

    private double duration;

    private double minDuration;

    private double maxDuration;

    private double reservationDeadline;

    private double cancellationDeadline;

    private double reservationConfirmation;

    private LocalTime workingStart;

    private LocalTime workingEnd;

    public Service(String name, String description, double price, int discount, Set<String> images, boolean isVisible,
                   StatusType status, boolean isAvailable, Category category, ServiceProductProvider provider,
                   Set<EventType> suggestedEventTypes, String specificity, double duration, double minDuration,
                   double maxDuration, double reservationDeadline, double cancellationDeadline,
                   double reservationConfirmation, LocalTime workingStart, LocalTime workingEnd){
        super(name, description, price, discount, images, isVisible, status, isAvailable, category, provider,
                suggestedEventTypes);
        this.specificity = specificity;
        this.duration = duration;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.reservationDeadline = reservationDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.reservationConfirmation = reservationConfirmation;
        this.workingStart=workingStart;
        this.workingEnd=workingEnd;
    }

    public String getSpecificity() {
        return specificity;
    }

    public void setSpecificity(String specificity) {
        this.specificity = specificity;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(double minDuration) {
        this.minDuration = minDuration;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public double getReservationDeadline() {
        return reservationDeadline;
    }

    public void setReservationDeadline(double reservationDeadline) {
        this.reservationDeadline = reservationDeadline;
    }

    public double getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(double cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public double getReservationConfirmation() {
        return reservationConfirmation;
    }

    public void setReservationConfirmation(double reservationConfirmation) {
        this.reservationConfirmation = reservationConfirmation;
    }

    public LocalTime getWorkingStart() {
        return workingStart;
    }

    public void setWorkingStart(LocalTime workingStart) {
        this.workingStart = workingStart;
    }

    public LocalTime getWorkingEnd() {
        return workingEnd;
    }

    public void setWorkingEnd(LocalTime workingEnd) {
        this.workingEnd = workingEnd;
    }
}
