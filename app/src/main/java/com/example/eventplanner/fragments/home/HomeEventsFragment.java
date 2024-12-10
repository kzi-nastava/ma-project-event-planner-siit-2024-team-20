package com.example.eventplanner.fragments.home;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.adapters.HomeEventsAdapter;
import com.example.eventplanner.helpers.SortMenuManager;
import com.example.eventplanner.helpers.FilterMenuManager;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.EventHome;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HomeEventsFragment extends Fragment {
    private RecyclerView topEventsRecyclerView,otherEventsRecyclerView;
    private HomeEventsAdapter topEventsAdapter;
    private HomeEventsAdapter otherEventsAdapter;
    private List<EventHome> topEventsList, otherEventsList;
    private int currentPage = 1; // Početna strana
    private boolean hasMorePages = true; // Pretpostavka da postoje sledeće stranice
    private Button btnPreviousPage, btnNextPage;
    private TextView currentPageText;

    public static HomeEventsFragment newInstance() {
        HomeEventsFragment fragment = new HomeEventsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public HomeEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SortMenuManager sortMenuManager = new SortMenuManager(requireContext());
        FilterMenuManager filterMenuManager = new FilterMenuManager(requireContext());

        ImageView sortEventsButton = view.findViewById(R.id.sort_events);
        sortEventsButton.setOnClickListener(v -> {
            sortMenuManager.showEventSortMenu(sortEventsButton);
        });

        // Filter Events Button
        ImageView filterEventsButton = view.findViewById(R.id.filter_events);
        filterEventsButton.setOnClickListener(v -> {
            filterMenuManager.showFilterEventsMenu(filterEventsButton);
        });
        // Povezivanje paginatora
        btnPreviousPage = view.findViewById(R.id.btn_previous_page);
        btnNextPage = view.findViewById(R.id.btn_next_page);
        currentPageText = view.findViewById(R.id.current_page_text);

        // Povezivanje RecyclerView-a
        otherEventsRecyclerView = view.findViewById(R.id.otherEventsRecyclerView);

        // Postavljanje inicijalne strane
        updatePaginator();

        // Postavljanje događaja za dugmad
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

        // Učitavanje prve stranice
        loadPage(currentPage);
    }
    private void updatePaginator() {
        // Ažurirajte tekst trenutne strane
        currentPageText.setText("Page " + currentPage);

        // Sakrij dugme za prethodnu stranu ako je trenutna strana 1
        btnPreviousPage.setVisibility(currentPage > 1 ? View.VISIBLE : View.GONE);

        // Sakrij dugme za sledeću stranu ako nema više stranica
        btnNextPage.setVisibility(hasMorePages ? View.VISIBLE : View.GONE);
    }
    private void loadPage(int page) {
        // Ovde biste obično napravili API poziv ili dobili podatke iz lokalnog izvora
        List<EventHome> events = fetchEventsForPage(page);

        // Ažurirajte RecyclerView adapter sa novim podacima
        HomeEventsAdapter adapter = (HomeEventsAdapter) otherEventsRecyclerView.getAdapter();
        if (adapter != null) {
            adapter.updateData(events);
        }

        // Proverite da li postoje još stranice
        hasMorePages = events.size() == 10; // Pretpostavljate da koristite fiksnu veličinu stranice

        // Ažurirajte paginator
        updatePaginator();
    }
    private List<EventHome> fetchEventsForPage(int page) {
        // Primer simuliranih podataka
        List<EventHome> allEvents =new ArrayList<>();//getAllEvents(); // Ovo dobijate iz API-ja ili baze
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(startIndex + 10, allEvents.size());

        if (startIndex >= allEvents.size()) return new ArrayList<>(); // Ako nema više podataka
        return allEvents.subList(startIndex, endIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_events, container, false);

        // Inicijalizacija RecyclerView-a
        topEventsRecyclerView = rootView.findViewById(R.id.topEventsRecyclerView);
        otherEventsRecyclerView = rootView.findViewById(R.id.otherEventsRecyclerView);

        // Postavljanje layout managera
        topEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        otherEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Priprema podataka (hardcodirani primeri)
        topEventsList = new ArrayList<>();
        otherEventsList = new ArrayList<>();

        // Kreiramo DateTimeFormatter za formatiranje datuma
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Dodavanje top događaja
        topEventsList.add(new EventHome(1L, "Event 1", "Description 1", "Type 1", "New York", LocalDateTime.parse("12/12/2024 09:00", formatter), LocalDateTime.parse("12/12/2024 12:00", formatter)));
        topEventsList.add(new EventHome(2L, "Event 2", "Description 2", "Type 2", "Los Angeles", LocalDateTime.parse("14/12/2024 10:00", formatter), LocalDateTime.parse("14/12/2024 14:00", formatter)));
        topEventsList.add(new EventHome(3L, "Event 3", "Description 3", "Type 3", "Chicago", LocalDateTime.parse("16/12/2024 15:00", formatter), LocalDateTime.parse("16/12/2024 18:00", formatter)));
        topEventsList.add(new EventHome(4L, "Event 4", "Description 4", "Type 4", "Miami", LocalDateTime.parse("18/12/2024 11:00", formatter), LocalDateTime.parse("18/12/2024 13:00", formatter)));
        topEventsList.add(new EventHome(5L, "Event 5", "Description 5", "Type 5", "San Francisco", LocalDateTime.parse("20/12/2024 09:00", formatter), LocalDateTime.parse("20/12/2024 12:00", formatter)));

        // Dodavanje drugih događaja
        otherEventsList.add(new EventHome(6L, "Event 6", "Description 6", "Type 6", "Dallas", LocalDateTime.parse("22/12/2024 10:00", formatter), LocalDateTime.parse("22/12/2024 13:00", formatter)));
        otherEventsList.add(new EventHome(7L, "Event 7", "Description 7", "Type 7", "Las Vegas", LocalDateTime.parse("24/12/2024 16:00", formatter), LocalDateTime.parse("24/12/2024 19:00", formatter)));
        otherEventsList.add(new EventHome(8L, "Event 8", "Description 8", "Type 8", "Seattle", LocalDateTime.parse("26/12/2024 14:00", formatter), LocalDateTime.parse("26/12/2024 17:00", formatter)));
        otherEventsList.add(new EventHome(9L, "Event 9", "Description 9", "Type 9", "Austin", LocalDateTime.parse("28/12/2024 09:00", formatter), LocalDateTime.parse("28/12/2024 12:00", formatter)));
        otherEventsList.add(new EventHome(10L, "Event 10", "Description 10", "Type 10", "Houston", LocalDateTime.parse("30/12/2024 13:00", formatter), LocalDateTime.parse("30/12/2024 16:00", formatter)));


        // Kreiranje adaptera
        topEventsAdapter = new HomeEventsAdapter(topEventsList, this::openEventDetailsActivity);
        otherEventsAdapter = new HomeEventsAdapter(otherEventsList, this::openEventDetailsActivity);

        // Postavljanje adaptera za oba RecyclerView-a
        topEventsRecyclerView.setAdapter(topEventsAdapter);
        otherEventsRecyclerView.setAdapter(otherEventsAdapter);

        return rootView;
    }
    private void openEventDetailsActivity(EventHome event) {
        Intent intent = new Intent(getContext(), EventDetailsActivity.class);
        intent.putExtra("event", event); // Prosleđivanje objekta
        startActivity(intent);
    }

}