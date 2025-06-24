package com.example.eventplanner.model.entities;

import com.example.eventplanner.model.entities.Role;

import java.util.Set;

public class EventOrganizer extends User{
    public EventOrganizer(Long id, String name, String lastName, String email, String password, String city, String address, String addressNum, String phoneNumber, String profileImage, boolean isActive, boolean isDeactivated, Set<Role> roles) {
        super(id, name, lastName, email, password, city, address, addressNum, phoneNumber, profileImage, isActive, isDeactivated, roles);
    }
}
