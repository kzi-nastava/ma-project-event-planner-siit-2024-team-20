package com.example.eventplanner.model.homepage;

import com.example.eventplanner.model.entities.Product;
import com.example.eventplanner.model.entities.Service;

public class ServiceProductHomeResponse {
    private Long id;
    private String type;
    private String name;
    private String description;
    private double price;
    private int discount;
    private boolean isAvailable;
    private String image;

    private String category;
    private String provider;

    public ServiceProductHomeResponse(Product product) {
        this.id = product.getId();
        this.type=product.getClass().getSimpleName();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.isAvailable=product.isAvailable();
        if(product.getImages()!=null)
            this.image = product.getImages().iterator().next();
        if(product.getCategory()!=null)this.category=product.getCategory().getName().toString();
        if(product.getProvider()!=null)this.provider=product.getProvider().getEmail().toString();
    }
    public ServiceProductHomeResponse(Service product) {
        this.id = product.getId();
        this.type=product.getClass().getSimpleName();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.isAvailable=product.isAvailable();
        if(product.getImages()!=null)
            this.image = product.getImages().iterator().next();
        if(product.getCategory()!=null)this.category=product.getCategory().getName().toString();
        if(product.getProvider()!=null)this.provider=product.getProvider().getEmail().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ServiceProductHomeResponse(Long id, String type, String name, String description, double price, int discount, boolean isAvailable, String image, String category, String provider) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.isAvailable = isAvailable;
        this.image = image;
        this.category = category;
        this.provider = provider;
    }
}
