package com.example.eventplanner.model;

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
    private String category;
    private String provider; // Zajedničko polje

    public Product(Long id, String name, String description, double price, int discount, String status,
                   boolean isAvailable, Set<String> images, String category, String provider) {
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

    @Override
    public String getCategory() { return category; }
    public String getStatus(){return status;}
    @Override
    public String getProvider() { return provider; }

    // Parcelable implementacija
    protected Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        discount = in.readInt();
        status = in.readString();
        isAvailable = in.readByte() != 0;
        images = new HashSet<>(in.createStringArrayList());
        category = in.readString();
        provider = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(discount);
        dest.writeString(status);
        dest.writeByte((byte) (isAvailable ? 1 : 0));
        dest.writeStringList(new ArrayList<>(images));
        dest.writeString(category);
        dest.writeString(provider);
    }
}
