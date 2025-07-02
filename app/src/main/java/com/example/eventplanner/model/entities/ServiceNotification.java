package com.example.eventplanner.model.entities;

import java.time.LocalDateTime;

public class ServiceNotification extends Notification{
    private Service service;;

    public ServiceNotification(String content, LocalDateTime timeOfSending, User receiver, Service service) {
        super(content, timeOfSending, receiver);
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
