package com.example.eventplanner.model.entities;

import android.os.Parcelable;

public abstract class BaseItem {
    public abstract Long getId();
    public abstract String getName();
    public abstract String getDescription();
    public abstract double getPrice();
    public abstract int getDiscount();
    public abstract boolean isAvailable();
    public abstract Category getCategory();
    public abstract User getProvider();
}
