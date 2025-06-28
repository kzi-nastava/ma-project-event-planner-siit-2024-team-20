package com.example.eventplanner.model.users;

public class ReportUserRequest {
    private Long reportedByUserId;
    private String reason;
    public ReportUserRequest(Long reportedByUserId,String reason) {
        this.reportedByUserId = reportedByUserId;
        this.reason = reason;
    }

    public Long getReportedByUserId() {
        return reportedByUserId;
    }

    public void setReportedByUserId(Long reportedByUserId) {
        this.reportedByUserId = reportedByUserId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ReportUserRequest() {
    }
}
