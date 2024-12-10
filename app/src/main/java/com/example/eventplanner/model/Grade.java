package com.example.eventplanner.model;

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
}
