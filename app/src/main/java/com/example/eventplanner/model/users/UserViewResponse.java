package com.example.eventplanner.model.users;

import com.example.eventplanner.model.entities.User;

public class UserViewResponse {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String profileImage;

    private String role;

    public UserViewResponse(Long id, String name, String lastName, String email,String profileImage,String role) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.profileImage=profileImage;
        this.role=role;
    }
    public UserViewResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
