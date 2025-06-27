package com.example.eventplanner.model.users;

import com.example.eventplanner.model.entities.User;

public class QuickUserResponse {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    boolean active;

    public QuickUserResponse(Long id, String name, String lastName, String email, boolean isActive) {
        super();
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.active = isActive;
    }
    public QuickUserResponse(User user) {
        super();
        this.id = user.getId();
        this.name = user.getName();
        this.lastName =user.getLastName();
        this.email = user.getEmail();
        this.active = user.isActive();
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        active = active;
    }
}
