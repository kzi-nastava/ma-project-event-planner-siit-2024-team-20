package com.example.eventplanner.fragments.home;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.adapters.HomeEventsAdapter;
import com.example.eventplanner.helpers.SortMenuManager;
import com.example.eventplanner.helpers.FilterMenuManager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.model.entities.EventHome;
import com.example.eventplanner.model.homepage.EventHomeResponse;
import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.services.spec.ApiService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class HomeEventsFragment extends Fragment {
    private RecyclerView topEventsRecyclerView, otherEventsRecyclerView;
    private HomeEventsAdapter topEventsAdapter;
    private HomeEventsAdapter otherEventsAdapter;
    private List<EventHome> topEventsList = new ArrayList<>();
    private List<EventHome> otherEventsList = new ArrayList<>();
    private int currentPage = 1;
    private boolean hasMorePages = true;
    private Button btnPreviousPage, btnNextPage;
    private TextView currentPageText;

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

        ImageView sortEventsButton = view.findViewById(R.id.sort_events);
        sortEventsButton.setOnClickListener(v -> sortMenuManager.showEventSortMenu(sortEventsButton));

        ImageView filterEventsButton = view.findViewById(R.id.filter_events);
        filterEventsButton.setOnClickListener(v -> filterMenuManager.showFilterEventsMenu(filterEventsButton));

        // Load data
        loadTopEvents();
        loadPage(currentPage);
    }

    private void loadTopEvents() {
        ApiService.getEventService().getTop5Events().enqueue(new Callback<List<EventHomeResponse>>() {
            @Override
            public void onResponse(Call<List<EventHomeResponse>> call, Response<List<EventHomeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EventHome> events = new ArrayList<>();
                    for (EventHomeResponse e : response.body()) {
                        events.add(new EventHome(
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
        String sort = "";

        Log.d("HomeEventsFragment", "Loading page: " + pageIndex + ", size: " + pageSize);

        ApiService.getEventService().getAllEventsPaged(pageIndex, pageSize, sort)
                .enqueue(new Callback<PagedResponse<EventHomeResponse>>() {
                    @Override
                    public void onResponse(Call<PagedResponse<EventHomeResponse>> call, Response<PagedResponse<EventHomeResponse>> response) {
                        Log.d("HomeEventsFragment", "Response code: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {
                            PagedResponse<EventHomeResponse> pagedData = response.body();
                            List<EventHomeResponse> content = pagedData.getContent();
                            Log.d("HomeEventsFragment", "Loaded events: " + content.size());
                            Log.d("HomeEventsFragment", "Is last page from backend: " + !hasMorePages);

                            /*if (content.isEmpty()) {
                                if (currentPage > 1) currentPage--;
                                hasMorePages = false;
                                updatePaginator();
                                Toast.makeText(getContext(), "No more events to show", Toast.LENGTH_SHORT).show();
                                return;
                            }*/

                            List<EventHome> events = new ArrayList<>();
                            for (EventHomeResponse e : content) {
                                Log.d("HomeEventsFragment", "Event: " + e.getName() + " at " + e.getLocation());
                                events.add(new EventHome(
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

                            hasMorePages = content.size() == pageSize && !pagedData.isLast(); // ili samo content.size() == pageSize

                            //currentPage = pagedData.getPageNumber() + 1;
                            updatePaginator();

                        } else {
                            Log.e("HomeEventsFragment", "Failed to load events, response code: " + response.code());
                            Toast.makeText(getContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PagedResponse<EventHomeResponse>> call, Throwable t) {
                        Log.e("HomeEventsFragment", "API failure: " + t.getClass().getSimpleName() + " - " + t.getMessage(), t);
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updatePaginator() {
        currentPageText.setText("Page " + currentPage);
        btnPreviousPage.setVisibility(currentPage > 1 ? View.VISIBLE : View.GONE);
        btnNextPage.setVisibility(hasMorePages ? View.VISIBLE : View.GONE);
    }

    private void openEventDetailsActivity(EventHome event) {
        Intent intent = new Intent(getContext(), EventDetailsActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);
    }
}
