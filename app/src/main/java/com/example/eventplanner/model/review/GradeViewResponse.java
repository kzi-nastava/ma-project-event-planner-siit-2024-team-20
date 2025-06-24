package com.example.eventplanner.model.review;

import com.example.eventplanner.model.entities.Grade;
import com.example.eventplanner.model.enumeration.GradeValue;

public class GradeViewResponse {
    private Long id;
    private GradeValue value;
    private String user;

    public GradeViewResponse(Grade grade) {
        this.value=grade.getValue();
        if(grade.getUser()!=null)this.user=grade.getUser().getEmail();
    }
    public GradeViewResponse(Long id,GradeValue value, String user) {
        this.id = id;
        this.value=value;
        this.user=user;
    }
}
