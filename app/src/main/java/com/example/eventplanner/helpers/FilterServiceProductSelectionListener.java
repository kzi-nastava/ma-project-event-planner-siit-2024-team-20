package com.example.eventplanner.helpers;

import java.util.List;

public interface FilterServiceProductSelectionListener {
    void onFilterSelected(List<String> categories, String type, double min, double max);
}
