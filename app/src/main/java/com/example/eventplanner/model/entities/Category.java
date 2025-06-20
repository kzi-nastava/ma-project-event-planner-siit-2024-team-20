package com.example.eventplanner.model.entities;

import com.example.eventplanner.model.enumeration.StatusType;

public class Category {
    private Long id;

    private String name;

    private String description;

    private StatusType status;

    public Category(Long id, String name, String description, StatusType status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Category() {
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

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
