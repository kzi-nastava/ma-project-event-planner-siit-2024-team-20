package com.example.eventplanner.model.productDetails;

import java.util.List;

public class FilterItemsOptions {
    private List<String> categoryOptions;

    public FilterItemsOptions(List<String> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }

    public List<String> getCategoryOptions() {
        return categoryOptions;
    }

    public void setCategoryOptions(List<String> categoryOptions) {
        this.categoryOptions = categoryOptions;
    }
}
