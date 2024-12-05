package com.example.eventplanner.model;

import java.util.Set;

public class EventType {
    private Long id;

    private String name;

    private String description;

    private Set<Category> categorySuggestions;

    private boolean isActive;

    public EventType(Long id, String name, String description, Set<Category> categorySuggestions) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.categorySuggestions = categorySuggestions;
        this.isActive = true;
    }
}
