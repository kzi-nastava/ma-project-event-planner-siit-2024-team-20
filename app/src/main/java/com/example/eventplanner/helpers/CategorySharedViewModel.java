package com.example.eventplanner.helpers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategorySharedViewModel extends ViewModel {
    private final MutableLiveData<String> selectedCategory = new MutableLiveData<>();

    public LiveData<String> getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String category) {
        selectedCategory.setValue(category);
    }
}

