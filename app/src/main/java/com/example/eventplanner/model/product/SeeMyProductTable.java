package com.example.eventplanner.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.eventplanner.model.BaseItem;

import java.util.HashSet;
import java.util.Set;

public class SeeMyProductTable implements Parcelable {
    private Long id;
    private String name;
    private String description;
    private double price;

    public SeeMyProductTable(Long id, String name, String description, double price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected SeeMyProductTable(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        price = in.readDouble();
    }

    public static final Creator<SeeMyProductTable> CREATOR = new Creator<SeeMyProductTable>() {
        @Override
        public SeeMyProductTable createFromParcel(Parcel in) {
            return new SeeMyProductTable(in);
        }

        @Override
        public SeeMyProductTable[] newArray(int size) {
            return new SeeMyProductTable[size];
        }
    };

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

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
    }
}
