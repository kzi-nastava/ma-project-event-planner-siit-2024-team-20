package com.example.eventplanner.model.entities;

import java.time.LocalDateTime;

public class ProductNotification extends Notification{
    private Product product;

    public ProductNotification(String content, LocalDateTime timeOfSending, User receiver) {
        super(content, timeOfSending, receiver);
        // TODO Auto-generated constructor stub
    }

    public ProductNotification(String content, LocalDateTime timeOfSending, User receiver, Product product) {
        super(content, timeOfSending, receiver);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
