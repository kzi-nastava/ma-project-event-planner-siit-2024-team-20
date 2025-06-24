package com.example.eventplanner.model.entities;
public class QuickUser extends User{

    public QuickUser(String name, String lastName, String email, String password) {
        super(name, lastName, email, password);
    }
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.getEmail();
    }
}