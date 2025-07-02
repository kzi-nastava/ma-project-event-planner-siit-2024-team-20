package com.example.eventplanner.model.entities;

import java.time.LocalDateTime;

public class EventNotification extends Notification{
    private Event event;

    public EventNotification(String content, LocalDateTime timeOfSending, User receiver) {
        super(content, timeOfSending, receiver);
        // TODO Auto-generated constructor stub
    }

    public EventNotification(String content, LocalDateTime timeOfSending, User receiver, Event event) {
        super(content, timeOfSending, receiver);
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
