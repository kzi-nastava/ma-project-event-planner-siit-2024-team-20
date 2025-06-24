package com.example.eventplanner.model.productManage;

import java.util.Set;

public class ProductCreationRequest {
    private String name;

    private String description;

    private double price;

    private int discount;

    private Set<String> images;

    private boolean isVisible;

    private boolean isAvailable;

    private String category;

    private Integer status;

    private Set<String> suggestedEventTypes;

    public ProductCreationRequest() {
    }

    public ProductCreationRequest(String name, String description, double price, int discount,
                                  Set<String> images, boolean isVisible, boolean isAvailable,
                                  String category, Integer status, Set<String> suggestedEventTypes) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.images = images;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.category = category;
        this.status = status;
        this.suggestedEventTypes = suggestedEventTypes;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<String> getSuggestedEventTypes() {
        return suggestedEventTypes;
    }

    public void setSuggestedEventTypes(Set<String> suggestedEventTypes) {
        this.suggestedEventTypes = suggestedEventTypes;
    }

}
