package com.example.eventplanner.fragments.home;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.helpers.SortMenuManager;
import com.example.eventplanner.helpers.FilterMenuManager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eventplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class HomeEventsFragment extends Fragment {

    public static HomeEventsFragment newInstance(String param1, String param2) {
        HomeEventsFragment fragment = new HomeEventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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

        // Sort i Filter menadžeri
        SortMenuManager sortMenuManager = new SortMenuManager(requireContext());
        FilterMenuManager filterMenuManager = new FilterMenuManager(requireContext());

        // Sort Events Button
        ImageView sortEventsButton = view.findViewById(R.id.sort_events);
        sortEventsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            sortMenuManager.showEventSortMenu(sortEventsButton);
        });

        // Filter Events Button
        ImageView filterEventsButton = view.findViewById(R.id.filter_events);
        filterEventsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            filterMenuManager.showFilterEventsMenu(filterEventsButton);
        });

        // Pronađi view-ove unutar layout-a fragmenta
        View eventCard1 = view.findViewById(R.id.event_card_1);
        View eventTopCard1 = view.findViewById(R.id.event5_card_1);

        // Postavi click listenere za svaki view
        eventCard1.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), EventDetailsActivity.class);
            intent.putExtra("event_id", "1"); // Dodaj potrebne podatke
            startActivity(intent);
        });

        eventTopCard1.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), EventDetailsActivity.class);
            intent.putExtra("event_id", "1"); // Dodaj potrebne podatke
            startActivity(intent);
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_events, container, false);
    }
}