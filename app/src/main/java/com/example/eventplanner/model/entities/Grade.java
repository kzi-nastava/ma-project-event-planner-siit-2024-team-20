package com.example.eventplanner.model.entities;

import com.example.eventplanner.model.enumeration.GradeValue;

public class Grade {
    private Long id;

    private GradeValue value;

    private User user;

    public Grade(Long id,GradeValue value, User user) {
        super();
        this.id = id;
        this.value = value;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GradeValue getValue() {
        return value;
    }

    public void setValue(GradeValue value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
