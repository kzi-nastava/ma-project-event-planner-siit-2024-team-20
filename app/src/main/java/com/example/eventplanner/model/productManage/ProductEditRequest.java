package com.example.eventplanner.model.productManage;

import java.util.Set;

public class ProductEditRequest {
    private Long id;

    private String name;

    private String description;

    private double price;

    private int discount;

    private Set<String> images;

    private boolean isVisible;

    private boolean isAvailable;

    private Set<String> suggestedEventTypes;

    public ProductEditRequest() {
    }

    public ProductEditRequest(Long id, String name, String description, double price, int discount,
                              Set<String> images, boolean isVisible, boolean isAvailable,
                              Set<String> suggestedEventTypes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.images = images;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
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

    public Set<String> getSuggestedEventTypes() {
        return suggestedEventTypes;
    }

    public void setSuggestedEventTypes(Set<String> suggestedEventTypes) {
        this.suggestedEventTypes = suggestedEventTypes;
    }
}
