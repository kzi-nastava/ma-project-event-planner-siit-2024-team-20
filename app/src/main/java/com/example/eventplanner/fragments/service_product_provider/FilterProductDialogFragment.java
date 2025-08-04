package com.example.eventplanner.fragments.service_product_provider;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.eventplanner.R;
import com.example.eventplanner.model.productManage.CategoryResponse;
import com.example.eventplanner.services.IProductService;
import com.example.eventplanner.services.spec.ApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterProductDialogFragment extends DialogFragment {

    private IProductService productService;

    public interface FilterDialogListener {
        void onFilterApplied(String category, String eventType, Double maxPrice, String availability);
    }

    private FilterDialogListener listener;

    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> eventTypeAdapter;

    private List<String> categories = new ArrayList<>();
    private List<String> eventTypes = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        productService = ApiService.getProductService();
    }
    public void setFilterDialogListener(FilterDialogListener listener) {
        this.listener = listener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_product_filter, container, false);

        Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        Spinner spinnerEventType = view.findViewById(R.id.spinnerEventType);
        EditText editMaxPrice = view.findViewById(R.id.editMaxPrice);
        Spinner spinnerAvailability = view.findViewById(R.id.spinnerAvailability);
        Button btnApply = view.findViewById(R.id.btnApplyFilters);

        categories.add("");
        eventTypes.add("");

        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
        eventTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, eventTypes);

        spinnerCategory.setAdapter(categoryAdapter);
        spinnerEventType.setAdapter(eventTypeAdapter);

        List<String> availabilities = Arrays.asList("", "Available", "Not available");
        spinnerAvailability.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, availabilities));

        loadCategories();
        loadEventTypes();

        btnApply.setOnClickListener(v -> {
            String category = (String) spinnerCategory.getSelectedItem();
            String eventType = (String) spinnerEventType.getSelectedItem();
            String availability = (String) spinnerAvailability.getSelectedItem();

            Double maxPrice = null;
            String priceText = editMaxPrice.getText().toString().trim();
            if (!priceText.isEmpty()) {
                try {
                    maxPrice = Double.parseDouble(priceText);
                } catch (NumberFormatException ignored) {}
            }

            listener.onFilterApplied(category, eventType, maxPrice, availability);
            dismiss();
        });

        return view;
    }

    private void loadCategories() {
        productService.getActiveSPCategories().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories.clear();
                    categories.add("");

                    for (String category : response.body()) {
                        categories.add(category);
                    }

                    if (categoryAdapter != null) {
                        categoryAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    private void loadEventTypes() {
        productService.getAllActiveEventTypesNames().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventTypes.clear();
                    eventTypes.add("");

                    eventTypes.addAll(response.body());

                    if (eventTypeAdapter != null) {
                        eventTypeAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.9);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
