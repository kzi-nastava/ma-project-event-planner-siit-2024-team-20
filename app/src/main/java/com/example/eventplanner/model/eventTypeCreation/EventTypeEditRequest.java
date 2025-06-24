package com.example.eventplanner.model.eventTypeCreation;

import java.util.Set;

public class EventTypeEditRequest {
    private Long id;
    private String name;
    private String description;
    private Set<String> categorySuggestions;

    public EventTypeEditRequest(Long id, String name, String description, Set<String> categorySuggestions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categorySuggestions = categorySuggestions;
    }

    public EventTypeEditRequest() {
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

    public Set<String> getCategorySuggestions() {
        return categorySuggestions;
    }

    public void setCategorySuggestions(Set<String> categorySuggestions) {
        this.categorySuggestions = categorySuggestions;
    }
}
