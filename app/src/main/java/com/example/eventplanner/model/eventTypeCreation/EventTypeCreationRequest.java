package com.example.eventplanner.model.eventTypeCreation;

import java.util.Set;

public class EventTypeCreationRequest {
    private String name;
    private String description;
    private Set<String> categorySuggestions;

    public EventTypeCreationRequest() {
    }

    public EventTypeCreationRequest(String name, String description, Set<String> categorySuggestions) {
        this.name = name;
        this.description = description;
        this.categorySuggestions = categorySuggestions;
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
