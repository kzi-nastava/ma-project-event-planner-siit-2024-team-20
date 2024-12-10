package com.example.eventplanner.fragments.service_product_provider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.model.product.EditProduct;

public class EditProductFragment extends Fragment {

    private EditText editName, editDescription, editPrice, editCategory;
    private NumberPicker npDiscount;
    private Switch switchVisibility, switchAvailability;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_product, container, false);
        EditProduct product = null;
        if (getArguments() != null) {
            product = getArguments().getParcelable("product");
        }

        // Ako je proizvod dostupan
        if (product != null) {
            EditText nameEditText = rootView.findViewById(R.id.editProduct_name);
            EditText descriptionEditText = rootView.findViewById(R.id.editProduct_description);
            EditText priceEditText = rootView.findViewById(R.id.editProduct_price);
            EditText categoryEditText = rootView.findViewById(R.id.editProduct_category);
            NumberPicker discountPicker = rootView.findViewById(R.id.npDiscount);
            Switch visibilitySwitch = rootView.findViewById(R.id.switchVisibility);
            Switch availabilitySwitch = rootView.findViewById(R.id.switchAvailability);

            categoryEditText.setText(product.getCategory());
            nameEditText.setText(product.getName());
            descriptionEditText.setText(product.getDescription());
            priceEditText.setText(String.valueOf(product.getPrice()));


            discountPicker.setMinValue(0);
            discountPicker.setMaxValue(100);
            discountPicker.setValue(product.getDiscount());

            visibilitySwitch.setChecked(product.isVisible());
            availabilitySwitch.setChecked(product.isAvailable());
        }
        return rootView;
    }
}