package com.example.eventplanner.model.homepage;

import com.example.eventplanner.model.entities.Product;

public class ProductHomeResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private String image;
    private String category;
    public ProductHomeResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description =product.getDescription();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.image = product.getImages().iterator().next();
        this.category = product.getCategory().getName();
    }
    public ProductHomeResponse(Long id,String name,String description,double price,
                           int discount,String image, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.category = category;
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
}
