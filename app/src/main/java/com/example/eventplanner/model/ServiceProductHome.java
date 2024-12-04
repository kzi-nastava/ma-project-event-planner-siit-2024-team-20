package com.example.eventplanner.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServiceProductHome implements Parcelable {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private String image;
    private String category;
    // Default constructor
    public ServiceProductHome() {
    }

    // Constructor with parameters
    public ServiceProductHome(Long id, String name, String description, double price, int discount, String image,String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.category=category;
    }

    // Getters and Setters
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
    // Parcelable methods
    protected ServiceProductHome(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        discount = in.readInt();
        image = in.readString();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(discount);
        dest.writeString(image);
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ServiceProductHome> CREATOR = new Creator<ServiceProductHome>() {
        @Override
        public ServiceProductHome createFromParcel(Parcel in) {
            return new ServiceProductHome(in);
        }

        @Override
        public ServiceProductHome[] newArray(int size) {
            return new ServiceProductHome[size];
        }
    };
    @Override
    public String toString() {
        return "ServiceHome{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", image='" + image + '\'' +
                '}';
    }
}
