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
}
