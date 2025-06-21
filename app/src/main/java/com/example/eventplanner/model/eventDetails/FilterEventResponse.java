package com.example.eventplanner.model.eventDetails;

import java.util.List;

public class FilterEventResponse {
    private List<String> typeOptions;
    private List<String> locationOptions;

    // Getteri i setteri

    public List<String> getTypeOptions() {
        return typeOptions;
    }

    public void setTypeOptions(List<String> typeOptions) {
        this.typeOptions = typeOptions;
    }

    public List<String> getLocationOptions() {
        return locationOptions;
    }

    public void setLocationOptions(List<String> locationOptions) {
        this.locationOptions = locationOptions;
    }
}
