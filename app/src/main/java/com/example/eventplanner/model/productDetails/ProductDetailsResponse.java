package com.example.eventplanner.model.productDetails;

import com.example.eventplanner.model.entities.Comment;
import com.example.eventplanner.model.entities.Grade;
import com.example.eventplanner.model.users.UserViewResponse;

import java.util.Set;

public class ProductDetailsResponse {
    private Long id;

    private String name;

    private String description;

    private double price;

    private int discount;

    private Set<String> images;

    private boolean isVisible;

    private boolean isAvailable;

    private String category;

    private UserViewResponse provider; //email

    private Set<Grade> grades;

    private Set<Comment> comments;

    private Set<String> suggestedEventTypes;

    public ProductDetailsResponse(Long id, String name, String description, double price, int discount, Set<String> images,
                                  boolean isVisible, boolean isAvailable, String category,
                                  UserViewResponse provider, Set<Grade> grades, Set<Comment> comments, Set<String> suggestedEventTypes) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.images = images;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.category = category;
        this.provider = provider;
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

    public UserViewResponse getProvider() {
        return provider;
    }

    public void setProvider(UserViewResponse provider) {
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

    public Set<String> getSuggestedEventTypes() {
        return suggestedEventTypes;
    }

    public void setSuggestedEventTypes(Set<String> suggestedEventTypes) {
        this.suggestedEventTypes = suggestedEventTypes;
    }
}
