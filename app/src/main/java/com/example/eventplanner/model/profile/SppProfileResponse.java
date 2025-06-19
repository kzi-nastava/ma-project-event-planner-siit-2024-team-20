package com.example.eventplanner.model.profile;

import com.example.eventplanner.model.entities.Event;

import java.util.Set;

public class SppProfileResponse {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String city;

    private String address;

    private String addressNum;

    private String phoneNumber;

    private String profileImage;

    boolean isActive;

    private String companyName;

    private String companyDescription;

    private Set<String> images;

    public SppProfileResponse() {
    }

    public SppProfileResponse(Long id, String name, String lastName, String email, String city,
                              String address, String addressNum, String phoneNumber, String profileImage,
                              boolean isActive,
                              String companyName, String companyDescription, Set<String> images) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.address = address;
        this.addressNum = addressNum;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.isActive = isActive;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.images = images;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNum() {
        return addressNum;
    }

    public void setAddressNum(String addressNum) {
        this.addressNum = addressNum;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }
}
