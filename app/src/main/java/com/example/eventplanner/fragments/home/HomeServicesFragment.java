package com.example.eventplanner.fragments.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.adapters.HomeItemsAdapter;
import com.example.eventplanner.helpers.FilterMenuManager;
import com.example.eventplanner.helpers.SortMenuManager;
import com.example.eventplanner.model.entities.BaseItem;
import com.example.eventplanner.model.entities.Product;
import com.example.eventplanner.model.entities.Service;
import com.example.eventplanner.model.entities.ServiceProductHome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeServicesFragment extends Fragment {

    private RecyclerView topServicesRecyclerView, otherServicesRecyclerView;
    private HomeItemsAdapter topServicesAdapter;
    private HomeItemsAdapter otherServicesAdapter;
    private List<BaseItem> topServicesList, otherServicesList;

    public HomeServicesFragment() {
        // Required empty public constructor
    }

    public static HomeServicesFragment newInstance() {
        HomeServicesFragment fragment = new HomeServicesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_services, container, false);

        // Inicijalizacija RecyclerView-a
        topServicesRecyclerView = rootView.findViewById(R.id.topProductsRecyclerView);
        otherServicesRecyclerView = rootView.findViewById(R.id.otherProductsRecyclerView);

        // Postavljanje LayoutManager-a
        topServicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        otherServicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicijalizacija podataka
        topServicesList = new ArrayList<>();
        otherServicesList = new ArrayList<>();

       /* topServicesList.add(new Service(1L, "Service 1", "Description 1", 100.0, 10, true, new HashSet<>(), "Specificity 1", "Category 1", 1.5, 1.0, 3.0, 1.0, 1.5, 0.5, "Provider A"));
        topServicesList.add(new Product(2L, "Product 1", "Description 2", 150.0, 20, "Active", true, new HashSet<>(), "Category A", "Provider B"));
        topServicesList.add(new Service(3L, "Service 2", "Description 3", 200.0, 15, true, new HashSet<>(), "Specificity 2", "Category 2", 2.0, 1.5, 3.5, 1.0, 2.0, 0.6, "Provider C"));
        topServicesList.add(new Product(4L, "Product 2", "Description 4", 250.0, 25, "Active", true, new HashSet<>(), "Category B", "Provider D"));
        topServicesList.add(new Service(5L, "Service 1", "Description 1", 100.0, 10, true, new HashSet<>(), "Specificity 1", "Category 1", 1.5, 1.0, 3.0, 1.0, 1.5, 0.5, "Provider A"));

        otherServicesList.add(new Service(6L, "Service 2", "Description 2", 200.0, 15, true, new HashSet<>(), "Specificity 2", "Category 2", 0, 1.5, 3.5, 1.0, 2.0, 0.6, "Provider B"));
        otherServicesList.add(new Product(7L, "Product 1", "Description 3", 150.0, 20, "Active", true, new HashSet<>(), "Category 1", "Provider C"));
        otherServicesList.add(new Service(8L, "Service 3", "Description 4", 250.0, 30, true, new HashSet<>(), "Specificity 3", "Category 3", 1.0, 1.0, 3.0, 0.5, 1.5, 0.4, "Provider D"));
        otherServicesList.add(new Product(9L, "Product 2", "Description 5", 180.0, 25, "Inactive", true, new HashSet<>(), "Category 2", "Provider E"));
        otherServicesList.add(new Service(10L, "Service 4", "Description 6", 300.0, 20, true, new HashSet<>(), "Specificity 4", "Category 4", 0, 1.2, 4.0, 1.2, 2.0, 0.7, "Provider F"));
*/

        // Kreiranje adaptera
        topServicesAdapter = new HomeItemsAdapter(topServicesList, this::openServiceProductDetailsActivity);
        otherServicesAdapter = new HomeItemsAdapter(otherServicesList, this::openServiceProductDetailsActivity);

        // Postavljanje adaptera
        topServicesRecyclerView.setAdapter(topServicesAdapter);
        otherServicesRecyclerView.setAdapter(otherServicesAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sort i Filter menadžeri
        SortMenuManager sortMenuManager = new SortMenuManager(requireContext());
        FilterMenuManager filterMenuManager = new FilterMenuManager(requireContext());

        ImageView sortServicesButton = view.findViewById(R.id.sort_products);
        sortServicesButton.setOnClickListener(v -> {
            sortMenuManager.showServicesProductsSortMenu(sortServicesButton);
        });

        ImageView filterServicesButton = view.findViewById(R.id.filter_products);
        filterServicesButton.setOnClickListener(v -> {
            filterMenuManager.showFilterServicesProductsMenu(filterServicesButton);
        });
    }

    private void openServiceProductDetailsActivity(BaseItem serviceProduct) {
        Intent intent = new Intent(getContext(), ServiceProductDetailsActivity.class);
        //intent.putExtra("baseItem", serviceProduct); // Prosleđuješ objekat
        startActivity(intent);
    }
}