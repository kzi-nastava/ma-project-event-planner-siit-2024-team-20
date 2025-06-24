package com.example.eventplanner.model.eventDetails;

import com.example.eventplanner.model.Location;

public class LocationResponse {
    private Long id;
    private String city;
    private String street;
    private String streetNumber;
    private double longitude;
    private double latitude;

    public LocationResponse() {
    }

    public LocationResponse(String city, String street, String streetNumber, double longitude, double latitude) {
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public LocationResponse(Location location) {
        this.id = location.getId();
        this.city = location.getCity();
        this.street =location.getStreet();
        this.streetNumber = location.getStreetNumber();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }
}
