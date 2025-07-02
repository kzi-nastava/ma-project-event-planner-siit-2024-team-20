package com.example.eventplanner.model.entities;

import com.example.eventplanner.model.enumeration.StatusType;

import java.time.LocalDateTime;

public class Report {
    private Long id;
    private User reportedUser;

    private User reportedBy;
    private String reason;
    private LocalDateTime createdAt = LocalDateTime.now();

    private StatusType status;

    public Report(User reportedUser, User reportedBy, String reason) {
        super();
        this.reportedUser = reportedUser;
        this.reportedBy = reportedBy;
        this.reason = reason;
        this.status=StatusType.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}