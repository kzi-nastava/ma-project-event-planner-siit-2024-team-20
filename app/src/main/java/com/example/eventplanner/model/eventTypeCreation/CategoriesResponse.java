package com.example.eventplanner.model.eventTypeCreation;

public class CategoriesResponse {
    private String name;

    public CategoriesResponse(String name) {
        this.name = name;
    }

    public CategoriesResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
