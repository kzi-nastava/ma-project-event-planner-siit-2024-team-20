package com.example.eventplanner.fragments.service_product_provider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.model.product.EditProduct;
import com.example.eventplanner.model.product.SeeMyProductTable;
import com.example.eventplanner.model.productManage.ProvidersProductsResponse;
import com.example.eventplanner.services.IProductService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeMyProductsFragment extends Fragment {

    private TableLayout productTable;
    private IProductService productService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_see_my_products, container, false);

        productTable = view.findViewById(R.id.product_table);

        productService = ApiService.getProductService();

        Long userId = (long) AuthService.getUserIdFromToken();
        if (userId != null) {
            loadProducts(userId);
        } else {
            Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadProducts(Long userId) {
        productService.getProvidersProducts(userId).enqueue(new Callback<List<ProvidersProductsResponse>>() {
            @Override
            public void onResponse(Call<List<ProvidersProductsResponse>> call, Response<List<ProvidersProductsResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    populateTable(response.body());
                } else {
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProvidersProductsResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateTable(List<ProvidersProductsResponse> products) {
        for (ProvidersProductsResponse product : products) {
            TableRow row = new TableRow(getContext());

            TableRow.LayoutParams cellParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4f);
            int padding = 16;
            int textColor = android.graphics.Color.parseColor("#f3eee9");

            TextView name = new TextView(getContext());
            name.setLayoutParams(cellParams);
            name.setText(product.getName());
            name.setTextColor(textColor);
            name.setPadding(padding, padding, padding, padding);
            name.setGravity(android.view.Gravity.CENTER);

            TextView price = new TextView(getContext());
            price.setLayoutParams(cellParams);
            price.setText(String.valueOf(product.getPrice()) + " EUR");
            price.setTextColor(textColor);
            price.setPadding(padding, padding, padding, padding);
            price.setGravity(android.view.Gravity.CENTER);


            Button editButton = new Button(getContext());
            editButton.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f));
            editButton.setText("Edit");
            editButton.setPadding(padding, padding, padding, padding);
            editButton.setOnClickListener(v -> {
                EditProductFragment editFragment = new EditProductFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedProduct", product);
                editFragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, editFragment)
                        .addToBackStack(null)
                        .commit();
            });

            row.addView(name);
            row.addView(price);
            row.addView(editButton);
            row.setPadding(40, 0, 0, 0);

            productTable.addView(row);
        }
    }



}