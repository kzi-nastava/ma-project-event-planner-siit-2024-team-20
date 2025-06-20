package com.example.eventplanner.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class EventType {
    private Long id;

    private String name;

    private String description;

    private Set<Category> categorySuggestions;

    @SerializedName("active")
    private boolean isActive;


    public EventType(Long id, String name, String description, Set<Category> categorySuggestions) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.categorySuggestions = categorySuggestions;
        this.isActive = true;
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

    public Set<Category> getCategorySuggestions() {
        return categorySuggestions;
    }

    public void setCategorySuggestions(Set<Category> categorySuggestions) {
        this.categorySuggestions = categorySuggestions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
