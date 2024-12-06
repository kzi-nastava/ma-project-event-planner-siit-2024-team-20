package com.example.eventplanner.model;

public class EventOrganizer extends User{
    public EventOrganizer(Long id, String name, String lastName, String email, String password, String city, String address, String addressNum, String phoneNumber, String profileImage) {
        super(id, name, lastName, email, password, city, address, addressNum, phoneNumber, profileImage);
    }
}
