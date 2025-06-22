package com.example.eventplanner.model.entities;

import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;

public abstract class User{
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
    private boolean isDeactivated;
    private boolean isNotificationMuted;
    private Timestamp lastPasswordResetDate;
    private String verificationToken;
    private Set<Role> roles;
    private Set<Event> acceptedEvents = new HashSet<>();
    private Set<Event> favouriteEvents = new HashSet<>();
    private Set<User> blockedUsers = new HashSet<>();
    public User(Long id, String name, String lastName, String email, String password, String city, String address,
                String addressNum, String phoneNumber, String profileImage, boolean isActive, boolean isDeactivated, Set<Role> roles) {
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
        this.isActive = isActive;
        this.isDeactivated = isDeactivated;
        this.roles = roles;
    }
    public User(String name, String lastName, String email, String password) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isNotificationMuted=false;
    }
    public User(String name, String lastName, String email, String password, String city, String address,
                String addressNum, String phoneNumber, String profileImage) {
        super();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.city = city;
        this.address = address;
        this.addressNum = addressNum;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.isNotificationMuted=false;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(boolean deactivated) {
        isDeactivated = deactivated;
    }

    public boolean isNotificationMuted() {
        return isNotificationMuted;
    }

    public void setNotificationMuted(boolean notificationMuted) {
        isNotificationMuted = notificationMuted;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Event> getAcceptedEvents() {
        return acceptedEvents;
    }

    public void setAcceptedEvents(Set<Event> acceptedEvents) {
        this.acceptedEvents = acceptedEvents;
    }

    public Set<Event> getFavouriteEvents() {
        return favouriteEvents;
    }

    public void setFavouriteEvents(Set<Event> favouriteEvents) {
        this.favouriteEvents = favouriteEvents;
    }

    public Set<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }
}
