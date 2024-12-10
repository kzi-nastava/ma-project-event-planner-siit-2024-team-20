package com.example.eventplanner.fragments.service_product_provider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.Product;
import com.example.eventplanner.model.product.SeeMyProductTable;

import java.util.ArrayList;
import java.util.List;

public class SeeMyProductsFragment extends Fragment {

    private TableLayout productTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_see_my_products, container, false);
        productTable = rootView.findViewById(R.id.product_table);

        List<SeeMyProductTable> productList = fetchProductsFromDatabase();

        for (SeeMyProductTable product : productList) {
            addProductRow(product);
        }
        return rootView;
    }

    private List<SeeMyProductTable> fetchProductsFromDatabase() {
        // simulacija podataka, (iz baze)
        List<SeeMyProductTable> products = new ArrayList<>();
        products.add(new SeeMyProductTable(1L, "Proizvod 1", "opis 1", 1000));
        products.add(new SeeMyProductTable(2L, "Proizvod 2", "opis 2", 1000));
        products.add(new SeeMyProductTable(3L, "Proizvod 3", "opis 3", 1000));
        return products;
    }
    private void addProductRow(SeeMyProductTable product) {
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        TextView nameColumn = createTextView(product.getName());
        TextView descriptionColumn = createTextView(product.getDescription());
        TextView priceColumn = createTextView(String.format("%.2f", product.getPrice()));

        tableRow.addView(nameColumn);
        tableRow.addView(descriptionColumn);
        tableRow.addView(priceColumn);

        // Postavljanje klikabilnosti reda
        tableRow.setClickable(true);
        /*tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        productTable.addView(tableRow);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(10, 10, 10, 10);
        textView.setLayoutParams(new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT, 1f
        ));
        textView.setTextColor(getResources().getColor(R.color.background_color));
        textView.setTextSize(14);
        return textView;
    }
}