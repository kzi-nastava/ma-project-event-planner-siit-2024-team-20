package com.example.eventplanner.helpers;

import java.util.List;

public interface FilterSelectionListener {
        void onFilterSelected(
                List<String> types,
                List<String> cities,
                String dateAfter,
                String dateBefore
        );

}
