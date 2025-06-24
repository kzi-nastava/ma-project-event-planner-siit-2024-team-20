package com.example.eventplanner.model.entities;

import android.os.Parcel;

import com.example.eventplanner.model.enumeration.StatusType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private Set<String> images=new HashSet<String>();
    private boolean isVisible;
    private boolean isAvailable;
    private StatusType status;
    private boolean isActive;
    private Category category;
    private Product previous;
    private ServiceProductProvider provider;
    private Set<Grade> grades=new HashSet<Grade>();
    private Set<Comment> comments=new HashSet<Comment>();

    private Set<EventType> suggestedEventTypes = new HashSet<>();

    public Product(String name, String description, double price, int discount,Set<String> images,
                   boolean isVisible, StatusType status, boolean isAvailable, Category category, ServiceProductProvider provider, Set<EventType> suggestedEventTypes) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.images = images;
        this.isVisible = isVisible;
        this.status = status;
        this.isAvailable = isAvailable;
        this.isActive=true;
        this.category = category;
        this.provider = provider;
        this.suggestedEventTypes = suggestedEventTypes;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product getPrevious() {
        return previous;
    }

    public void setPrevious(Product previous) {
        this.previous = previous;
    }

    public ServiceProductProvider getProvider() {
        return provider;
    }

    public void setProvider(ServiceProductProvider provider) {
        this.provider = provider;
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

    public Set<EventType> getSuggestedEventTypes() {
        return suggestedEventTypes;
    }

    public void setSuggestedEventTypes(Set<EventType> suggestedEventTypes) {
        this.suggestedEventTypes = suggestedEventTypes;
    }
}
