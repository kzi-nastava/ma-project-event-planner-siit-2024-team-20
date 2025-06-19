package com.example.eventplanner.model.entities;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Product extends BaseItem {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private String status;
    private boolean isAvailable;
    private Set<String> images = new HashSet<>();
    private Category category;
    private String provider; // Zajedničko polje

    public Product(Long id, String name, String description, double price, int discount, String status,
                   boolean isAvailable, Set<String> images,Category category, String provider) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.status = status;
        this.isAvailable = isAvailable;
        this.images = images;
        this.category = category;
        this.provider = provider;
    }

    // Getteri i implementacija BaseItem metoda
    @Override
    public Long getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public double getPrice() { return price; }

    @Override
    public int getDiscount() { return discount; }

    @Override
    public boolean isAvailable() { return isAvailable; }


    public String getStatus(){return status;}
    @Override
    public String getProvider() { return provider; }

    @Override
    public Category getCategory() {
        return category;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
