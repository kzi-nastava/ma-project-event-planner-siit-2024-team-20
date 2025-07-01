package com.example.eventplanner.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import java.time.LocalDateTime;

public class Notification {

    private Long id;
    private String content;
    private LocalDateTime timeOfSending;
    private boolean isRead;
    private User receiver;
    public Notification(String content, LocalDateTime timeOfSending, User receiver) {
        super();
        this.content = content;
        this.timeOfSending = timeOfSending;
        this.receiver = receiver;
    }

    public Notification() {
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

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
