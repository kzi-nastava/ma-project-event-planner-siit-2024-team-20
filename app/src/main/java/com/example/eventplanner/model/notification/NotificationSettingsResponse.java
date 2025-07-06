package com.example.eventplanner.model.notification;

public class NotificationSettingsResponse {
    private boolean notificationsMuted;

    public boolean isNotificationsMuted() {
        return notificationsMuted;
    }

    public void setNotificationsMuted(boolean notificationsMuted) {
        this.notificationsMuted = notificationsMuted;
    }

    public NotificationSettingsResponse(boolean notificationsMuted) {
        super();
        this.notificationsMuted = notificationsMuted;
    }
}
