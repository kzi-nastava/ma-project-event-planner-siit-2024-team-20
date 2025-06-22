package com.example.eventplanner.fragments.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.adapters.HomeItemsAdapter;
import com.example.eventplanner.helpers.FilterMenuManager;
import com.example.eventplanner.helpers.SortMenuManager;
import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.model.homepage.ServiceProductHomeResponse;
import com.example.eventplanner.services.spec.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeServicesFragment extends Fragment {

    private RecyclerView topServicesRecyclerView, otherServicesRecyclerView;
    private HomeItemsAdapter topServicesAdapter;
    private HomeItemsAdapter otherServicesAdapter;
    private List<ServiceProductHomeResponse> topServicesList, otherServicesList;
    private Button btnPreviousPage, btnNextPage;
    private TextView currentPageText;
    private SearchView searchView;

    private int currentPage = 1;
    private boolean hasMorePages = true;
    private String searchQuery = "";
    private String selectedSort = "";

    private enum ActiveFilterType { NONE, SEARCH, SORT,FILTER }
    private ActiveFilterType activeFilterType = ActiveFilterType.NONE;

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

        // Kreiranje adaptera
        topServicesAdapter = new HomeItemsAdapter(topServicesList, this::openServiceProductDetailsActivity);
        otherServicesAdapter = new HomeItemsAdapter(otherServicesList, this::openServiceProductDetailsActivity);

        // Postavljanje adaptera
        topServicesRecyclerView.setAdapter(topServicesAdapter);
        otherServicesRecyclerView.setAdapter(otherServicesAdapter);

        btnPreviousPage = rootView.findViewById(R.id.btn_previous_page);
        btnNextPage = rootView.findViewById(R.id.btn_next_page);
        currentPageText = rootView.findViewById(R.id.current_page_text);

        btnPreviousPage.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                loadPage(currentPage);
            }
        });

        btnNextPage.setOnClickListener(v -> {
            if (hasMorePages) {
                currentPage++;
                loadPage(currentPage);
            }
        });
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

        loadTopServicesProducts();
        loadPage(currentPage);


    }
    private static final String TAG = "HomeServicesFragment";
    private void loadTopServicesProducts() {
        ApiService.getProductService().getTop5ServicesProducts().enqueue(new Callback<List<ServiceProductHomeResponse>>() {
            @Override
            public void onResponse(Call<List<ServiceProductHomeResponse>> call, Response<List<ServiceProductHomeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ServiceProductHomeResponse> topServices = new ArrayList<>();
                    for (ServiceProductHomeResponse p : response.body()) {
                        topServices.add(new ServiceProductHomeResponse(
                                p.getId(),
                                p.getType(),
                                p.getName(),
                                p.getDescription(),
                                p.getPrice(),
                                p.getDiscount(),
                                p.isAvailable(),
                                p.getImage(),
                                p.getCategory(),
                                p.getProvider()
                        ));
                    }

                    topServicesAdapter.updateData(topServices);
                } else {
                    Toast.makeText(getContext(), "Failed to load top services/products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ServiceProductHomeResponse>> call, Throwable t) {
                Log.e("HomeServicesFragment", "API failure: " + t.getClass().getSimpleName() + " - " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPage(int page) {
        int pageIndex = page - 1;
        int pageSize = 10;
        this.currentPage = page;

        Log.d(TAG, "Calling loadPage(" + page + "), pageIndex=" + pageIndex);

        Call<PagedResponse<ServiceProductHomeResponse>> call =
                ApiService.getProductService().getPagedProducts(pageIndex, pageSize, "");

        call.enqueue(new Callback<PagedResponse<ServiceProductHomeResponse>>() {
            @Override
            public void onResponse(Call<PagedResponse<ServiceProductHomeResponse>> call, Response<PagedResponse<ServiceProductHomeResponse>> response) {
                Log.d(TAG, "onResponse: success=" + response.isSuccessful());

                if (response.isSuccessful() && response.body() != null) {
                    PagedResponse<ServiceProductHomeResponse> pagedData = response.body();
                    List<ServiceProductHomeResponse> content = pagedData.getContent();
                    List<ServiceProductHomeResponse> products = new ArrayList<>();
                    for (ServiceProductHomeResponse p : content) {
                            products.add(new ServiceProductHomeResponse(
                                    p.getId(),
                                    p.getType(),
                                    p.getName(),
                                    p.getDescription(),
                                    p.getPrice(),
                                    p.getDiscount(),
                                    p.isAvailable(),
                                    p.getImage(),
                                    p.getCategory(),
                                    p.getProvider()
                            ));

                    }


                    otherServicesAdapter.updateData(products);
                    hasMorePages = currentPage < pagedData.getTotalPages();
                    updatePaginator();
                } else {
                    Log.w(TAG, "Response failed: " + response.code());
                    Toast.makeText(getContext(), "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<ServiceProductHomeResponse>> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePaginator() {
        currentPageText.setText("Page " + currentPage);
        btnPreviousPage.setVisibility(currentPage > 1 ? View.VISIBLE : View.GONE);
        btnNextPage.setVisibility(hasMorePages ? View.VISIBLE : View.GONE);
    }


    private void openServiceProductDetailsActivity(ServiceProductHomeResponse serviceProduct) {
        Intent intent = new Intent(getContext(), ServiceProductDetailsActivity.class);
        //intent.putExtra("baseItem", serviceProduct); // Prosleđuješ objekat
        startActivity(intent);
    }
}