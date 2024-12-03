package com.example.eventplanner.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class EventHome implements Parcelable {
    private Long id;
    private String name;
    private String description;
    private String eventType;
    private String location;
    private String image;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    // Konstruktor bez argumenata
    public EventHome() {}

    // Konstruktor sa svim poljima
    public EventHome(Long id, String name, String description, String eventType, String location, String image, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.location = location;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Konstruktor za čitanje iz Parcel-a
    protected EventHome(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        eventType = in.readString();
        location = in.readString();
        image = in.readString();
        startDate = (LocalDateTime) in.readSerializable();
        endDate = (LocalDateTime) in.readSerializable();
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
        dest.writeString(eventType);
        dest.writeString(location);
        dest.writeString(image);
        dest.writeSerializable(startDate);
        dest.writeSerializable(endDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EventHome> CREATOR = new Creator<EventHome>() {
        @Override
        public EventHome createFromParcel(Parcel in) {
            return new EventHome(in);
        }

        @Override
        public EventHome[] newArray(int size) {
            return new EventHome[size];
        }
    };

    // Getteri i setteri
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "EventHomeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", eventType='" + eventType + '\'' +
                ", location='" + location + '\'' +
                ", image='" + image + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
