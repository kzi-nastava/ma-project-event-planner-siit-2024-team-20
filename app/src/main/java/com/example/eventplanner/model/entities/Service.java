package com.example.eventplanner.model.entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.os.Parcel;
import java.util.HashSet;
import java.util.Set;

public class Service extends BaseItem {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private boolean isAvailable;
    private Set<String> images = new HashSet<>();
    private String specificity;
    private Category category;
    private double duration;
    private double minDuration;
    private double maxDuration;
    private double reservationDeadline;
    private double cancellationDeadline;
    private double reservationConfirmation;
    private User provider; // Zajedničko polje
    private LocalTime workingStart;

    private LocalTime workingEnd;
    private Set<Grade> grades=new HashSet<Grade>();

    private Set<Comment> comments=new HashSet<Comment>();
    public Service(Long id, String name, String description, double price, int discount, boolean isAvailable,
                   Set<String> images, String specificity, Category category, double duration, double minDuration,
                   double maxDuration, double reservationDeadline, double cancellationDeadline,
                   double reservationConfirmation,User provider) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.isAvailable = isAvailable;
        this.images = images;
        this.specificity = specificity;
        this.category = category;
        this.duration = duration;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.reservationDeadline = reservationDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.reservationConfirmation = reservationConfirmation;
        this.provider = provider;
    }

    // Getteri i implementacija BaseItem metoda
    @Override
    public Long getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public double getPrice() { return price; }

    @Override
    public int getDiscount() { return discount; }

    @Override
    public boolean isAvailable() { return isAvailable; }

    @Override
    public Category getCategory() { return category; }
    public String getSpecificity(){return specificity;}
    public double getDuration(){ return duration; }
    public double getMaxDuration(){ return maxDuration; }
    public double getMinDuration(){ return minDuration; }
    @Override
    public User getProvider() { return provider; }

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

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public void setSpecificity(String specificity) {
        this.specificity = specificity;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setMinDuration(double minDuration) {
        this.minDuration = minDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
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
