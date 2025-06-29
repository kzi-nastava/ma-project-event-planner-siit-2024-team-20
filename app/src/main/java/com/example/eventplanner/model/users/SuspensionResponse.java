package com.example.eventplanner.model.users;

import java.time.LocalDateTime;

public class SuspensionResponse {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UserResponse user;
    //response
    public SuspensionResponse(Long id, LocalDateTime startDate, LocalDateTime endDate, UserResponse user) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
