package com.example.eventplanner.model.notification;

import com.example.eventplanner.model.entities.EventNotification;
import com.example.eventplanner.model.entities.Notification;
import com.example.eventplanner.model.entities.ProductNotification;
import com.example.eventplanner.model.entities.ServiceNotification;

import java.time.LocalDateTime;

public class NotificationResponse {
    private Long id;
    private String content;
    private LocalDateTime timeOfSending;
    private boolean isRead;
    private String type;
    private Long referenceId;
    public NotificationResponse(Long id,String content,LocalDateTime timeOfSending,
                           boolean isRead, String type, Long referenceId) {
        super();
        this.id = id;
        this.content = content;
        this.timeOfSending = timeOfSending;
        this.isRead = isRead;
        this.type = type;
        this.referenceId=referenceId;
    }
    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.timeOfSending = notification.getTimeOfSending();
        this.isRead = notification.isRead();
        if(notification instanceof EventNotification) {
            EventNotification eventNotification = (EventNotification) notification;
            type="EVENT";
            referenceId=eventNotification.getEvent().getId();
        }
        else if(notification instanceof ServiceNotification) {
            ServiceNotification serviceNotification = (ServiceNotification) notification;
            type="SERVICE";
            referenceId=serviceNotification.getService().getId();
        }
        else {
            ProductNotification productNotification = (ProductNotification) notification;
            type="PRODUCT";
            referenceId=productNotification.getProduct().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeOfSending() {
        return timeOfSending;
    }

    public void setTimeOfSending(LocalDateTime timeOfSending) {
        this.timeOfSending = timeOfSending;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
}
