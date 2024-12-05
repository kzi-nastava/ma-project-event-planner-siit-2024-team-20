package com.example.eventplanner.model;

import java.util.Set;

public abstract class User {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String city;
    private String address;
    private String addressNum;
    private String phoneNumber;
    private String profileImage;
    private boolean isActive;
    private Set<Event> acceptedEvents;
    private Set<Event> favouriteEvents;

    public User(Long id, String name, String lastName, String email, String password, String city, String address,
                String addressNum, String phoneNumber, String profileImage) {
        super();
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.city = city;
        this.address = address;
        this.addressNum = addressNum;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }
}
