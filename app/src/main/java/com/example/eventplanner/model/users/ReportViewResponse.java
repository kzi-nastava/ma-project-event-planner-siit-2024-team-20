package com.example.eventplanner.model.users;

import com.example.eventplanner.model.entities.Report;
import com.example.eventplanner.model.enumeration.StatusType;

import java.time.LocalDateTime;

public class ReportViewResponse {
        private Long id;
        private String reason;
        private String reportedUserName;
        private String reportedUserLastName;
        private String reportedUserEmail;
        private String reportedByUserEmail;
        private Long reportedUserId;
        private LocalDateTime createdAt;
        private StatusType status;
        public ReportViewResponse(Long id, String reason,String reportedUserName,
                             String reportedUserLastName,String reportedUserEmail,
                              String reportedByUserEmail,Long reportedUserId,LocalDateTime createdAt,StatusType status) {
            super();
            this.id = id;
            this.reason = reason;
            this.reportedUserName = reportedUserName;
            this.reportedUserLastName = reportedUserLastName;
            this.reportedUserEmail = reportedUserEmail;
            this.reportedByUserEmail = reportedByUserEmail;
            this.reportedUserId=reportedUserId;
            this.createdAt = createdAt;
            this.status=status;
        }
        public ReportViewResponse(Report report) {
            super();
            this.id = report.getId();
            this.reason = report.getReason();
            this.reportedUserName = report.getReportedUser().getName();
            this.reportedUserLastName = report.getReportedUser().getLastName();
            this.reportedUserEmail = report.getReportedUser().getEmail();
            this.reportedByUserEmail = report.getReportedBy().getEmail();
            this.reportedUserId=report.getReportedUser().getId();
            this.createdAt =report.getCreatedAt();
            this.status=report.getStatus();
        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReportedUserName() {
        return reportedUserName;
    }

    public void setReportedUserName(String reportedUserName) {
        this.reportedUserName = reportedUserName;
    }

    public String getReportedUserLastName() {
        return reportedUserLastName;
    }

    public void setReportedUserLastName(String reportedUserLastName) {
        this.reportedUserLastName = reportedUserLastName;
    }

    public String getReportedUserEmail() {
        return reportedUserEmail;
    }

    public void setReportedUserEmail(String reportedUserEmail) {
        this.reportedUserEmail = reportedUserEmail;
    }

    public String getReportedByUserEmail() {
        return reportedByUserEmail;
    }

    public void setReportedByUserEmail(String reportedByUserEmail) {
        this.reportedByUserEmail = reportedByUserEmail;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
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
