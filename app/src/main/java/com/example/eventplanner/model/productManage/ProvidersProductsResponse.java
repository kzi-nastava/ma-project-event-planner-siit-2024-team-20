package com.example.eventplanner.model.productManage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Set;

public class ProvidersProductsResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private Set<String> images;
    @SerializedName("visible")
    private boolean isVisible;
    @SerializedName("available")
    private boolean isAvailable;
    private String category;
    private Set<String> grades;
    private Set<String> comments;
    private Set<String> suggestedEventTypes;

    public ProvidersProductsResponse(Long id, String name, String description, double price, int discount,
                                     Set<String> images, boolean isVisible, boolean isAvailable, String category,
                                     Set<String> grades, Set<String> comments, Set<String> suggestedEventTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.images = images;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.category = category;
        this.grades = grades;
        this.comments = comments;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getGrades() {
        return grades;
    }

    public void setGrades(Set<String> grades) {
        this.grades = grades;
    }

    public Set<String> getComments() {
        return comments;
    }

    public void setComments(Set<String> comments) {
        this.comments = comments;
    }

    public Set<String> getSuggestedEventTypes() {
        return suggestedEventTypes;
    }

    public void setSuggestedEventTypes(Set<String> suggestedEventTypes) {
        this.suggestedEventTypes = suggestedEventTypes;
    }
}
