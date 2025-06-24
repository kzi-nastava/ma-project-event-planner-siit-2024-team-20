package com.example.eventplanner.model.productDetails;

import com.example.eventplanner.model.entities.Comment;
import com.example.eventplanner.model.entities.Grade;

import java.util.Set;

public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private double price;

    private int discount;

    private Set<String> images;

    private boolean isVisible;

    private boolean isAvailable;

    private String category;

    private String provider; //email

    private Set<Grade> grades;

    private Set<Comment> comments;

    private Set<String> suggestedEventTypes;

    public ProductResponse(Long id, String name, String description, double price, int discount, Set<String> images,
                      boolean isVisible, boolean isAvailable, String category,
                      String provider, Set<Grade> grades, Set<Comment> comments, Set<String> suggestedEventTypes) {
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

}
