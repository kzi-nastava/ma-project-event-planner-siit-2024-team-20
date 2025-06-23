package com.example.eventplanner.fragments.service_product_provider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.fragments.service_product.create_product.ImagesPickerFragment;
import com.example.eventplanner.helpers.TempImageHolder;
import com.example.eventplanner.model.product.EditProduct;
import com.example.eventplanner.model.productManage.ProductEditRequest;
import com.example.eventplanner.model.productManage.ProvidersProductsResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductFragment extends Fragment {

    private List<String> allEventTypes = new ArrayList<>();
    private boolean[] selectedItems;
    private ArrayList<String> selectedEventTypes = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_product, container, false);

        ProvidersProductsResponse product = (ProvidersProductsResponse) getArguments().getSerializable("selectedProduct");

        if (product == null) {
            Toast.makeText(getContext(), "No product data", Toast.LENGTH_SHORT).show();
            return view;
        }
        

        loadEventTypes(product);

        EditText categoryEditText = view.findViewById(R.id.editProduct_category);
        EditText nameEditText = view.findViewById(R.id.editProduct_name);
        EditText descriptionEditText = view.findViewById(R.id.editProduct_description);
        EditText priceEditText = view.findViewById(R.id.editProduct_price);
        NumberPicker discountPicker = view.findViewById(R.id.npDiscount);
        Switch visibilitySwitch = view.findViewById(R.id.switchVisibility);
        Switch availabilitySwitch = view.findViewById(R.id.switchAvailability);

        discountPicker.setMinValue(0);
        discountPicker.setMaxValue(100);

        categoryEditText.setText(product.getCategory());
        nameEditText.setText(product.getName());
        descriptionEditText.setText(product.getDescription());
        priceEditText.setText(String.valueOf(product.getPrice()));
        discountPicker.setValue(product.getDiscount());
        visibilitySwitch.setChecked(product.isVisible());
        availabilitySwitch.setChecked(product.isAvailable());


        Set<String> imagesSet = product.getImages();
        ArrayList<String> base64Images = new ArrayList<>(imagesSet);
        ImagesPickerFragment imagesPickerFragment = new ImagesPickerFragment();

        TempImageHolder TempImageHolder = new TempImageHolder();
        TempImageHolder.backendImages = new ArrayList<>(base64Images);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerViewEditImages4, imagesPickerFragment);
        transaction.commit();

        Button saveButton = view.findViewById(R.id.editProduct_save);
        saveButton.setOnClickListener(v -> {
            sendEditProductRequest(product, nameEditText, descriptionEditText, priceEditText, discountPicker,
                    visibilitySwitch, availabilitySwitch);
        });


        Button deactivateButton = view.findViewById(R.id.editProduct_delete);
        deactivateButton.setOnClickListener(v -> showDeactivateConfirmationDialog(product.getId()));


        return view;
    }
    private void loadEventTypes(ProvidersProductsResponse product) {
        ApiService.getProductService().getAllActiveEventTypesNames()
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            allEventTypes = response.body();
                            setupEventTypesDialog(product);
                        } else {
                            Toast.makeText(getContext(), "Error loading event types", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void setupEventTypesDialog(ProvidersProductsResponse product) {
        selectedItems = new boolean[allEventTypes.size()];

        Set<String> existingTypes = product.getSuggestedEventTypes();

        for (int i = 0; i < allEventTypes.size(); i++) {
            if (existingTypes.contains(allEventTypes.get(i))) {
                selectedItems[i] = true;
                selectedEventTypes.add(allEventTypes.get(i));
            }
        }

        TextView tvETypes = requireView().findViewById(R.id.tvETypse);
        tvETypes.setText(String.join(", ", selectedEventTypes));

        MaterialCardView cardView = requireView().findViewById(R.id.selectEType);
        cardView.setOnClickListener(v -> {
            showEventTypeDialog(tvETypes);
        });
    }
    private void showEventTypeDialog(TextView tvETypes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select event types");

        String[] eventTypeArray = allEventTypes.toArray(new String[0]);

        builder.setMultiChoiceItems(eventTypeArray, selectedItems, (dialog, which, isChecked) -> {
            if (isChecked) {
                selectedEventTypes.add(eventTypeArray[which]);
            } else {
                selectedEventTypes.remove(eventTypeArray[which]);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> {
            tvETypes.setText(String.join(", ", selectedEventTypes));
        });

        builder.setNegativeButton("Decline", null);
        builder.show();
    }
    private void sendEditProductRequest(ProvidersProductsResponse product,
                                        EditText nameEditText,
                                        EditText descriptionEditText,
                                        EditText priceEditText,
                                        NumberPicker discountPicker,
                                        Switch visibilitySwitch,
                                        Switch availabilitySwitch) {

        ImagesPickerFragment imagesFragment = (ImagesPickerFragment)
                getChildFragmentManager().findFragmentById(R.id.fragmentContainerViewEditImages4);

        Set<String> currentImages = new HashSet<>();
        if (imagesFragment != null) {
            currentImages = imagesFragment.getAllImages();
        }

        ProductEditRequest request = new ProductEditRequest(
                product.getId(),
                nameEditText.getText().toString(),
                descriptionEditText.getText().toString(),
                Double.parseDouble(priceEditText.getText().toString()),
                discountPicker.getValue(),
                currentImages,
                visibilitySwitch.isChecked(),
                availabilitySwitch.isChecked(),
                new HashSet<>(selectedEventTypes)
        );

        ApiService.getProductService().editProduct(product.getId(), request)
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && response.body() != null && response.body()) {
                            Toast.makeText(getContext(), "Product updated successfully", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDeactivateConfirmationDialog(Long productId) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes", (dialog, which) -> deactivateProduct(productId))
                .setNegativeButton("No", null)
                .show();
    }

    private void deactivateProduct(Long productId) {
        ApiService.getProductService().deactivateProduct(productId)
                .enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                            Toast.makeText(requireContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(requireContext(), "Failed to delete product", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}