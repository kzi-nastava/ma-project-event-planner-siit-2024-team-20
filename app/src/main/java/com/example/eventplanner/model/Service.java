package com.example.eventplanner.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.os.Parcel;
import java.util.HashSet;
import java.util.Set;

public class Service extends BaseItem {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int discount;
    private boolean isAvailable;
    private Set<String> images = new HashSet<>();
    private String specificity;
    private String category;
    private double duration;
    private double minDuration;
    private double maxDuration;
    private double reservationDeadline;
    private double cancellationDeadline;
    private double reservationConfirmation;
    private String provider; // Zajedničko polje

    public Service(Long id, String name, String description, double price, int discount, boolean isAvailable,
                   Set<String> images, String specificity, String category, double duration, double minDuration,
                   double maxDuration, double reservationDeadline, double cancellationDeadline,
                   double reservationConfirmation, String provider) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.isAvailable = isAvailable;
        this.images = images;
        this.specificity = specificity;
        this.category = category;
        this.duration = duration;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.reservationDeadline = reservationDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.reservationConfirmation = reservationConfirmation;
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
    public String getSpecificity(){return specificity;}
    public double getDuration(){ return duration; }
    public double getMaxDuration(){ return maxDuration; }
    public double getMinDuration(){ return minDuration; }
    @Override
    public String getProvider() { return provider; }

    // Parcelable implementacija
    protected Service(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
        discount = in.readInt();
        isAvailable = in.readByte() != 0;
        images = new HashSet<>(in.createStringArrayList());
        specificity = in.readString();
        category = in.readString();
        duration = in.readDouble();
        minDuration = in.readDouble();
        maxDuration = in.readDouble();
        reservationDeadline = in.readDouble();
        cancellationDeadline = in.readDouble();
        reservationConfirmation = in.readDouble();
        provider = in.readString();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
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
        dest.writeByte((byte) (isAvailable ? 1 : 0));
        dest.writeStringList(new ArrayList<>(images));
        dest.writeString(specificity);
        dest.writeString(category);
        dest.writeDouble(duration);
        dest.writeDouble(minDuration);
        dest.writeDouble(maxDuration);
        dest.writeDouble(reservationDeadline);
        dest.writeDouble(cancellationDeadline);
        dest.writeDouble(reservationConfirmation);
        dest.writeString(provider);
    }
}
