package com.example.eventplanner.model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import java.time.LocalDateTime;

public class Notification implements Parcelable {

    private Long id;
    private String content;
    private LocalDateTime timeOfSending;
    private boolean isRead;
    private String itemTitle;

    // Default constructor
    public Notification() {
    }

    // Constructor for initializing the class
    public Notification(Long id, String content, LocalDateTime timeOfSending, boolean isRead, String itemTitle) {
        this.id = id;
        this.content = content;
        this.timeOfSending = timeOfSending;
        this.isRead = isRead;
        this.itemTitle = itemTitle;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeOfSending() {
        return timeOfSending;
    }

    public void setTimeOfSending(LocalDateTime timeOfSending) {
        this.timeOfSending = timeOfSending;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    // Parcelable implementation

    protected Notification(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        content = in.readString();
        timeOfSending = (LocalDateTime) in.readSerializable(); // LocalDateTime implements Serializable
        isRead = in.readByte() != 0;
        itemTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(content);
        dest.writeSerializable(timeOfSending); // LocalDateTime is written as Serializable
        dest.writeByte((byte) (isRead ? 1 : 0));
        dest.writeString(itemTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
