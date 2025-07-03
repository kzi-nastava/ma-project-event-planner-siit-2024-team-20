package com.example.eventplanner.fragments.home;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.adapters.HomeItemsAdapter;
import com.example.eventplanner.helpers.FilterMenuManager;
import com.example.eventplanner.helpers.FilterSelectionListener;
import com.example.eventplanner.helpers.FilterServiceProductSelectionListener;
import com.example.eventplanner.helpers.SortMenuManager;
import com.example.eventplanner.helpers.SortSelectionListener;
import com.example.eventplanner.helpers.SortServiceProductSelectionListener;
import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.model.homepage.ServiceProductHomeResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;

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
public class HomeServicesFragment extends Fragment implements SortServiceProductSelectionListener, FilterServiceProductSelectionListener {

    private RecyclerView topServicesRecyclerView, otherServicesRecyclerView;
    private HomeItemsAdapter topServicesAdapter;
    private HomeItemsAdapter otherServicesAdapter;
    private List<ServiceProductHomeResponse> topServicesList, otherServicesList;
    private Button btnPreviousPage, btnNextPage;
    private TextView currentPageText;

    private int currentPage = 1;
    private boolean hasMorePages = true;
    private String searchQuery = "";
    private List<String> selectedCategories = new ArrayList<>();
    private String selectedType = null;
    private double minPrice = 0.0;
    private double maxPrice = 0.0;
    private List<String> selectedSortCriteria = new ArrayList<>();
    private String selectedSortOrder = "asc";


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
        //Log.d("ANDJA", AuthService.getRoleFromToken());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sort i Filter menadžeri
        SortMenuManager sortMenuManager = new SortMenuManager(requireContext());
        FilterMenuManager filterMenuManager = new FilterMenuManager(requireContext());

        sortMenuManager.setSortSelectionListener(this);
        filterMenuManager.setFilterSelectionListener(this);

        ImageView sortServicesButton = view.findViewById(R.id.sort_products);
        sortServicesButton.setOnClickListener(v -> {
            sortMenuManager.showServicesProductsSortMenu(sortServicesButton);
        });

        ImageView filterServicesButton = view.findViewById(R.id.filter_products);
        filterServicesButton.setOnClickListener(v -> {
            filterMenuManager.showFilterServicesProductsMenu((View) filterServicesButton,selectedCategories,selectedType, (float) minPrice,(float) maxPrice);
        });
        SearchView searchView = view.findViewById(R.id.search_view_products);
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                searchView.clearFocus(); // sklanja kursor
            }
        });
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> {
                // Resetuj tekst
                searchView.setQuery("", false);

                // Resetuj sve filtere
                searchQuery = null;
                loadPage(1);

                // Sakrij tastaturu i ukloni fokus
                searchView.clearFocus();
                hideKeyboard(searchView);
            });
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.trim().isEmpty()) {
                    searchQuery = query.trim();
                    loadPage(1);

                    searchView.clearFocus(); // bitno za skrivanje tastature
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchQuery = "";
                    loadPage(1);

                    hideKeyboard(searchView);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            searchView.postDelayed(() -> {
                searchView.clearFocus();
                hideKeyboard(searchView);
            }, 50);
            return false;
        });

        loadTopServicesProducts();
        loadPage(currentPage);


    }
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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

        Call<PagedResponse<ServiceProductHomeResponse>> call;

        call = ApiService.getProductService().getPagedProducts(
                        searchQuery,
                        selectedType,
                        selectedCategories,
                        minPrice,
                        maxPrice,
                        selectedSortCriteria,
                        selectedSortOrder,
                        pageIndex, pageSize
        );

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PagedResponse<ServiceProductHomeResponse>> call, Response<PagedResponse<ServiceProductHomeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PagedResponse<ServiceProductHomeResponse> pagedData = response.body();
                    List<ServiceProductHomeResponse> content = pagedData.getContent();
                    otherServicesAdapter.updateData(content);
                    hasMorePages = currentPage < pagedData.getTotalPages();
                    updatePaginator();

                    Log.d(TAG, "Response success: " + response.isSuccessful());
                    if (response.body() != null) {
                        Log.d(TAG, "Response body total items: " + response.body().getContent().size());
                        Log.d(TAG, "Response body total pages: " + response.body().getTotalPages());
                        Log.d(TAG, "Current page: " + currentPage);
                    } else {
                        Log.d(TAG, "Response body is null");
                    }

                } else {
                    Toast.makeText(getContext(), "Failed to load services/products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<ServiceProductHomeResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "selectedType=" + selectedType + ", selectedCategories=" + selectedCategories.toString());

        if (page == 1) {
            otherServicesRecyclerView.scrollToPosition(0);
        }
    }
    @Override
    public void onSortSelected(List<String> criteria, String order) {
        this.selectedSortCriteria = criteria;
        this.selectedSortOrder = order;
        loadPage(1);
    }
    @Override
    public void onFilterSelected(List<String> categories, String type, double min, double max) {
        this.selectedCategories = categories;
        this.selectedType = type;
        this.minPrice = min;
        this.maxPrice = max;
        loadPage(1);
    }

    private void updatePaginator() {
        currentPageText.setText("Page " + currentPage);
        btnPreviousPage.setVisibility(currentPage > 1 ? View.VISIBLE : View.GONE);
        btnNextPage.setVisibility(hasMorePages ? View.VISIBLE : View.GONE);
    }
    private void openServiceProductDetailsActivity(ServiceProductHomeResponse serviceProduct) {
        Intent intent = new Intent(getContext(), ServiceProductDetailsActivity.class);
        intent.putExtra("itemId", serviceProduct.getId());
        intent.putExtra("itemType", serviceProduct.getType());
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadPage(1);
    }
}