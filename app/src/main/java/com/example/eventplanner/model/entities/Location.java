package com.example.eventplanner.model;

public class Location {
    private Long id;

    private String city;

    private String street;

    private String streetNumber;

    private double longitude;

    private double latitude;

    public Location(Long id, String city, String street, String streetNumber, double longitude, double latitude) {
        super();
        this.id = id;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
