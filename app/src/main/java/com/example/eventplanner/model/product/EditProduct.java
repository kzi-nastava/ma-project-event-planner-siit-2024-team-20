package com.example.eventplanner.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public class EditProduct implements Parcelable{
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private boolean isVisible;
    private boolean isAvailable;
    private Set<String> images = new HashSet<>();
    private String category;
    private Set<String> eventCategories = new HashSet<>();

    public EditProduct() {}
    public EditProduct(Long id, String name, String description,
                       double price, int discount, boolean isVisible,
                       boolean isAvailable, Set<String> images, String category, Set<String> eventCategories){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.isVisible = isVisible;
        this.isAvailable = isAvailable;
        this.images = images;
        this.category = category;
        this.eventCategories = eventCategories;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    public boolean isVisible() { return isVisible; }
    public void setVisible(boolean visible) { isVisible = visible; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public Set<String> getImages() { return images; }
    public void setImages(Set<String> images) { this.images = images; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Set<String> getEventCategories() { return eventCategories; }
    public void setEventCategories(Set<String> eventCategories) { this.eventCategories = eventCategories; }

    protected EditProduct(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        discount = in.readInt();
        isVisible = in.readByte() != 0;
        isAvailable = in.readByte() != 0;
        category = in.readString();
    }

    public static final Creator<EditProduct> CREATOR = new Creator<EditProduct>() {
        @Override
        public EditProduct createFromParcel(Parcel in) {
            return new EditProduct(in);
        }

        @Override
        public EditProduct[] newArray(int size) {
            return new EditProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
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
        dest.writeByte((byte) (isVisible ? 1 : 0));
        dest.writeByte((byte) (isAvailable ? 1 : 0));
        dest.writeString(category);
    }
}
