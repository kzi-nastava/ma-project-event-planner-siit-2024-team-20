package com.example.eventplanner.fragments.service_product.create_product;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.helpers.CategorySharedViewModel;
import com.example.eventplanner.helpers.TempImageHolder;
import com.example.eventplanner.model.productManage.ProductCreationRequest;
import com.example.eventplanner.model.productManage.ProductResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;
import com.google.android.material.card.MaterialCardView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProductFragment extends Fragment {
    private EditText etName, etDescription, etPrice;
    private NumberPicker npDiscount;
    private Switch switchVisibility, switchAvailability;
    private Button btnCreate;
    private MaterialCardView selectETypeCard;
    private TextView tvEventTypes;

    private List<String> allEventTypes;
    private boolean[] selectedEventTypes;
    private Set<String> selectedEventTypeList = new HashSet<>();

    private ImagesPickerFragment galleryFragment;

    private Switch switchChangeFragment;
    private CategorySharedViewModel sharedViewModel;
    private String selectedCategory = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_product, container, false);

        switchChangeFragment = root.findViewById(R.id.switchChangeFragment);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(CategorySharedViewModel.class);

        switchChangeFragment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            FragmentManager fm = getChildFragmentManager();
            Fragment fragment = isChecked ? new NewCategoryFragment() : new ExistingCategoriesFragment();
            fm.beginTransaction()
                    .replace(R.id.fragmentContainerView3, fragment)
                    .commit();
        });

        sharedViewModel.getSelectedCategory().observe(getViewLifecycleOwner(), category -> {
            if (category != null && !category.trim().isEmpty()) {
                selectedCategory = category.trim();
            }
        });


        etName = root.findViewById(R.id.editTextText10);
        etDescription = root.findViewById(R.id.editTextTextMultiLine);
        etPrice = root.findViewById(R.id.editTextNumber);
        npDiscount = root.findViewById(R.id.npDiscount);

        switchVisibility = root.findViewById(R.id.switchVisibility);
        switchAvailability = root.findViewById(R.id.switchAvailability);

        btnCreate = root.findViewById(R.id.button);
        selectETypeCard = root.findViewById(R.id.selectEType);
        tvEventTypes = root.findViewById(R.id.tvETypse);

        npDiscount.setMinValue(0);
        npDiscount.setMaxValue(100);
        npDiscount.setValue(0);

        fetchEventTypes();

        selectETypeCard.setOnClickListener(v -> showEventTypesDialog());

        btnCreate.setOnClickListener(view -> createProduct());

        FragmentManager fm = getChildFragmentManager();
        TempImageHolder.backendImages.clear();
        galleryFragment = new ImagesPickerFragment();
        fm.beginTransaction()
                .replace(R.id.fragmentContainerView4, galleryFragment)
                .commit();

        return root;
    }

    private void fetchEventTypes() {
        ApiService.getProductService()
                .getAllActiveEventTypesNames()
                .enqueue(new Callback<List<String>>() {
                    @Override public void onResponse(Call<List<String>> call, Response<List<String>> resp) {
                        if (resp.isSuccessful() && resp.body() != null) {
                            allEventTypes = resp.body();
                            selectedEventTypes = new boolean[allEventTypes.size()];
                        } else {
                            Toast.makeText(requireContext(), "No event types available.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onFailure(Call<List<String>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void showEventTypesDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose event types");
        builder.setMultiChoiceItems(allEventTypes.toArray(new String[0]),
                selectedEventTypes,
                (dialog, which, isChecked) -> {
                    if (isChecked) selectedEventTypeList.add(allEventTypes.get(which));
                    else selectedEventTypeList.remove(allEventTypes.get(which));
                });
        builder.setPositiveButton("Confirm", (d, w) ->
                tvEventTypes.setText(selectedEventTypeList.isEmpty()
                        ? " "
                        : String.join(", ", selectedEventTypeList))
        );
        builder.setNegativeButton("Cancel", (d, w) -> d.dismiss());
        builder.show();
    }

    private void createProduct() {
        String name = etName.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();

        if (name.isEmpty() || desc.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in the basic fields..", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Incorrect price\n.", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<String> images = galleryFragment.getAllImages();
        boolean isVisible = switchVisibility.isChecked();
        boolean isAvailable = switchAvailability.isChecked();

        if (selectedCategory.isEmpty()) {
            Toast.makeText(requireContext(), "Choose or enter new category", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductCreationRequest req = new ProductCreationRequest(
                name,
                desc,
                price,
                npDiscount.getValue(),
                images,
                isVisible,
                isAvailable,
                selectedCategory,
                1,
                selectedEventTypeList
        );


        long providerId = AuthService.getUserIdFromToken();
        ApiService.getProductService()
                .addProduct(providerId, req)
                .enqueue(new Callback<ProductResponse>() {
                    @Override public void onResponse(Call<ProductResponse> call, Response<ProductResponse> resp) {
                        if (resp.isSuccessful()) {
                            Toast.makeText(requireContext(), "Product successfully created!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            Toast.makeText(requireContext(), "Error while creating\n.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onFailure(Call<ProductResponse> call, Throwable t) {
                        Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

}
