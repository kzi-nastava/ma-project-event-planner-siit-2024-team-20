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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
