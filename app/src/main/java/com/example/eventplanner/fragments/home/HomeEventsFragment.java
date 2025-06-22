package com.example.eventplanner.fragments.home;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.adapters.HomeEventsAdapter;
import com.example.eventplanner.helpers.FilterSelectionListener;
import com.example.eventplanner.helpers.SortMenuManager;
import com.example.eventplanner.helpers.FilterMenuManager;

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
import com.example.eventplanner.helpers.SortSelectionListener;
import com.example.eventplanner.model.homepage.EventHomeResponse;
import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.services.spec.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HomeEventsFragment extends Fragment implements SortSelectionListener, FilterSelectionListener {
    private RecyclerView topEventsRecyclerView, otherEventsRecyclerView;
    private HomeEventsAdapter topEventsAdapter;
    private HomeEventsAdapter otherEventsAdapter;
    private List<EventHomeResponse> topEventsList = new ArrayList<>();
    private List<EventHomeResponse> otherEventsList = new ArrayList<>();
    private int currentPage = 1;
    private boolean hasMorePages = true;
    private Button btnPreviousPage, btnNextPage;
    private TextView currentPageText;
    private enum ActiveFilterType {
        NONE,
        SEARCH,
        FILTER,
        SORT
    }

    private ActiveFilterType activeFilterType = ActiveFilterType.NONE;

    // Podaci za kriterijume
    private String searchQuery = "";
    private List<String> filterTypes = new ArrayList<>();
    private List<String> filterCities = new ArrayList<>();
    private String filterDateAfter = null;
    private String filterDateBefore = null;
    private List<String> selectedSortCriteria = new ArrayList<>();
    private String selectedSortOrder = "asc";


    public static HomeEventsFragment newInstance() {
        return new HomeEventsFragment();
    }

    public HomeEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_events, container, false);

        topEventsRecyclerView = rootView.findViewById(R.id.topEventsRecyclerView);
        otherEventsRecyclerView = rootView.findViewById(R.id.otherEventsRecyclerView);
        btnPreviousPage = rootView.findViewById(R.id.btn_previous_page);
        btnNextPage = rootView.findViewById(R.id.btn_next_page);
        currentPageText = rootView.findViewById(R.id.current_page_text);

        topEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        otherEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        topEventsAdapter = new HomeEventsAdapter(topEventsList, this::openEventDetailsActivity);
        otherEventsAdapter = new HomeEventsAdapter(otherEventsList, this::openEventDetailsActivity);

        topEventsRecyclerView.setAdapter(topEventsAdapter);
        otherEventsRecyclerView.setAdapter(otherEventsAdapter);

        // Dugmad za paginaciju
        btnPreviousPage.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                Log.d("HomeEventsFragment", "Previous clicked, currentPage = " + currentPage);
                loadPage(currentPage);
            }
        });


        btnNextPage.setOnClickListener(v -> {
            if (hasMorePages) {
                currentPage++;
                Log.d("HomeEventsFragment", "Next clicked, currentPage = " + currentPage);
                loadPage(currentPage);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sort i filter
        SortMenuManager sortMenuManager = new SortMenuManager(requireContext());
        FilterMenuManager filterMenuManager = new FilterMenuManager(requireContext());

        sortMenuManager.setSortSelectionListener(this);
        filterMenuManager.setFilterSelectionListener(this);

        ImageView sortEventsButton = view.findViewById(R.id.sort_events);
        sortEventsButton.setOnClickListener(v -> sortMenuManager.showEventSortMenu(sortEventsButton,selectedSortCriteria,selectedSortOrder));

        ImageView filterEventsButton = view.findViewById(R.id.filter_events);
        filterEventsButton.setOnClickListener(v -> filterMenuManager.showFilterEventsMenu(filterEventsButton,filterTypes,filterCities,filterDateAfter,filterDateBefore));


        SearchView searchView = view.findViewById(R.id.search_view_events);
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
                activeFilterType = ActiveFilterType.NONE;
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
                    selectedSortCriteria = new ArrayList<>();
                    selectedSortOrder = "asc";
                    filterTypes = new ArrayList<>();
                    filterCities = new ArrayList<>();
                    filterDateAfter = null;
                    filterDateBefore = null;

                    searchQuery = query.trim();
                    activeFilterType = ActiveFilterType.SEARCH;
                    loadPage(1);

                    searchView.clearFocus(); // bitno za skrivanje tastature
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchQuery = null;
                    activeFilterType = ActiveFilterType.NONE;
                    loadPage(1);

                    // Samo sakrij tastaturu, ali ne zatvaraj SearchView — jer je korisnik možda samo brisao
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

        // Load data
        loadTopEvents();
        loadPage(currentPage);
    }
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void loadTopEvents() {
        ApiService.getEventService().getTop5Events().enqueue(new Callback<List<EventHomeResponse>>() {
            @Override
            public void onResponse(Call<List<EventHomeResponse>> call, Response<List<EventHomeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EventHomeResponse> events = new ArrayList<>();
                    for (EventHomeResponse e : response.body()) {
                        events.add(new EventHomeResponse(
                                e.getId(),
                                e.getName(),
                                e.getDescription(),
                                e.getEventType(),
                                e.getLocation(),
                                e.getStartDate(),
                                e.getEndDate()
                        ));
                    }
                    topEventsAdapter.updateData(events);
                } else {
                    Toast.makeText(getContext(), "Failed to load top events", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<EventHomeResponse>> call, Throwable t) {
                Log.e("HomeEventsFragment", "API failure: " + t.getClass().getSimpleName() + " - " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void loadPage(int page) {
        int pageIndex = page - 1;
        int pageSize = 10;
        this.currentPage = page;

        Call<PagedResponse<EventHomeResponse>> call;

        switch (activeFilterType) {
            case SEARCH:
                if (searchQuery == null || searchQuery.trim().isEmpty()) {
                    // Ako je search prazan, učitaj sve
                    call = ApiService.getEventService().getAllEventsPaged(pageIndex, pageSize, "");


                } else {
                    call = ApiService.getEventService().searchEvents(searchQuery.trim(), pageIndex, pageSize, "");
                    Log.d("HomeEventsFragment", "Searching with query: " + searchQuery);
                }
                break;

            case FILTER:
                call = ApiService.getEventService().filterEvents(
                        filterTypes,
                        filterCities,
                        filterDateAfter,
                        filterDateBefore,
                        pageIndex,
                        pageSize,
                        ""
                );
                break;

            case SORT:
                if (selectedSortCriteria == null || selectedSortCriteria.isEmpty()) {
                    call = ApiService.getEventService().getAllEventsPaged(pageIndex, pageSize, "");
                } else {
                    call = ApiService.getEventService().getSortedEvents(pageIndex, pageSize, selectedSortCriteria, selectedSortOrder);
                }
                break;

            case NONE:
            default:
                call = ApiService.getEventService().getAllEventsPaged(pageIndex, pageSize, "");
                break;
        }
        Log.d("HomeEventsFragment", "loadPage called. activeFilterType = " + activeFilterType + ", searchQuery = " + searchQuery);

        call.enqueue(new Callback<PagedResponse<EventHomeResponse>>() {
            @Override
            public void onResponse(Call<PagedResponse<EventHomeResponse>> call, Response<PagedResponse<EventHomeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PagedResponse<EventHomeResponse> pagedData = response.body();
                    List<EventHomeResponse> content = pagedData.getContent();
                    List<EventHomeResponse> events = new ArrayList<>();
                    for (EventHomeResponse e : content) {
                        events.add(new EventHomeResponse(
                                e.getId(),
                                e.getName(),
                                e.getDescription(),
                                e.getEventType(),
                                e.getLocation(),
                                e.getStartDate(),
                                e.getEndDate()
                        ));
                    }

                    otherEventsAdapter.updateData(events);
                    hasMorePages = currentPage < pagedData.getTotalPages();
                    updatePaginator();
                } else {
                    Toast.makeText(getContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<EventHomeResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updatePaginator() {
        currentPageText.setText("Page " + currentPage);
        btnPreviousPage.setVisibility(currentPage > 1 ? View.VISIBLE : View.GONE);
        btnNextPage.setVisibility(hasMorePages ? View.VISIBLE : View.GONE);
    }

    private void openEventDetailsActivity(EventHomeResponse event) {
        Intent intent = new Intent(getContext(), EventDetailsActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }

    @Override
    public void onSortSelected(List<String> criteria, String order) {
        this.selectedSortCriteria = criteria;
        this.selectedSortOrder = order;
        this.activeFilterType = ActiveFilterType.SORT;

        // Reset ostalog
        this.searchQuery = "";
        this.filterTypes = new ArrayList<>();
        this.filterCities = new ArrayList<>();
        this.filterDateAfter = null;
        this.filterDateBefore = null;

        loadPage(1);
    }

    @Override
    public void onFilterSelected(List<String> types, List<String> cities, String dateAfter, String dateBefore) {
        this.filterTypes = types;
        this.filterCities = cities;
        this.filterDateAfter = dateAfter;
        this.filterDateBefore = dateBefore;
        this.activeFilterType = ActiveFilterType.FILTER;

        // Reset ostalog
        this.searchQuery = "";
        this.selectedSortCriteria = new ArrayList<>();
        this.selectedSortOrder = "asc";

        loadPage(1);
    }

}
