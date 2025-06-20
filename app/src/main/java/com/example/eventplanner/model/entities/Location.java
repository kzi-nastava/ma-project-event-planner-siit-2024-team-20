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
}
