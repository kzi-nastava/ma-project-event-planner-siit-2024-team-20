package com.example.eventplanner.fragments.service_product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.model.homepage.EventHomeResponse;
import com.example.eventplanner.model.serviceReservation.ServiceBookingRequest;
import com.example.eventplanner.services.spec.ApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingServiceFragment extends Fragment {
    private Long serviceId;
    private Spinner eventDropdown, dateDropdown;
    private NumberPicker fromHourPicker, fromMinutePicker, toHourPicker, toMinutePicker;
    private Button submitButton;
    private List<EventHomeResponse> userEvents = new ArrayList<>();

    public BookingServiceFragment() {
        // Required empty public constructor
    }

    public static BookingServiceFragment newInstance(Long serviceId) {
        BookingServiceFragment fragment = new BookingServiceFragment();
        Bundle args = new Bundle();
        args.putLong("serviceId", serviceId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchUserEvents();
        if (getArguments() != null) {
            serviceId = getArguments().getLong("serviceId", -1);
        }
        // Initialize views
        eventDropdown = view.findViewById(R.id.event_spinner);
        dateDropdown = view.findViewById(R.id.date_spinner);
        fromHourPicker = view.findViewById(R.id.fromHourPicker);
        fromMinutePicker = view.findViewById(R.id.fromMinutePicker);
        toHourPicker = view.findViewById(R.id.toHourPicker);
        toMinutePicker = view.findViewById(R.id.toMinutePicker);
        submitButton = view.findViewById(R.id.confirm_booking_button);

        setupNumberPickers();
        populateEventDropdown();
        setupDropdownListeners();
        // Submit button
        submitButton.setOnClickListener(v -> {
            if (validateReservation()) {
                //sendBookingRequest();
            }
        });

    }
    private void setupDropdownListeners() {
        eventDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EventHomeResponse selectedEvent = userEvents.get(position);
                if (selectedEvent != null) {
                    List<String> dates = generateDatesBetween(selectedEvent.getStartDate(), selectedEvent.getEndDate());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dates);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dateDropdown.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private List<String> generateDatesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = startDateTime.toLocalDate();
        LocalDate end = endDateTime.toLocalDate();

        while (!start.isAfter(end)) {
            dates.add(start.format(formatter));
            start = start.plusDays(1);
        }

        return dates;
    }


    private void updateDateDropdown(String selectedEvent) {
        List<String> dates = getDatesForEvent(selectedEvent);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateDropdown.setAdapter(adapter);
    }
    private void populateDateDropdown(String event) {
        // Dobijamo datume za izabrani događaj
        List<String> dates = getDatesForEvent(event);

        // Kreiranje adaptera za datume
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Postavljanje adaptera na Spinner sa datumima
        dateDropdown.setAdapter(dateAdapter);
    }
    private void populateEventDropdown() {
        List<String> events = Arrays.asList("Event 1", "Event 2", "Event 3");

        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, events);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eventDropdown.setAdapter(eventAdapter);

        eventDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedEvent = (String) parentView.getItemAtPosition(position);
                populateDateDropdown(selectedEvent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Ako ništa nije selektovano
            }
        });
    }

    private List<String> getDatesForEvent(String event) {
        if (event.equals("Event 1")) {
            return Arrays.asList("2024-12-05", "2024-12-06");
        } else if (event.equals("Event 2")) {
            return Arrays.asList("2024-12-07", "2024-12-08");
        }
        return Collections.emptyList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout za fragment
        View view = inflater.inflate(R.layout.fragment_booking_service, container, false);

        return view;
    }

    private void setupNumberPickers() {
        fromHourPicker.setMinValue(0);
        fromHourPicker.setMaxValue(23);
        fromMinutePicker.setMinValue(0);
        fromMinutePicker.setMaxValue(59);

        toHourPicker.setMinValue(0);
        toHourPicker.setMaxValue(23);
        toMinutePicker.setMinValue(0);
        toMinutePicker.setMaxValue(59);
    }
    private boolean validateReservation() {
        if (eventDropdown.getSelectedItem() == null || eventDropdown.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select an event.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateDropdown.getSelectedItem() == null || dateDropdown.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please select a date.", Toast.LENGTH_SHORT).show();
            return false;
        }
        int fromHour = fromHourPicker.getValue();
        int fromMinute = fromMinutePicker.getValue();
        int toHour = toHourPicker.getValue();
        int toMinute = toMinutePicker.getValue();

        int fromTimeInMinutes = fromHour * 60 + fromMinute;
        int toTimeInMinutes = toHour * 60 + toMinute;

        if (toTimeInMinutes <= fromTimeInMinutes) {
            Toast.makeText(getContext(), "Invalid time range: 'To' must be after 'From'", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void fetchUserEvents() {

        ApiService.getEventService().getMyEvents().enqueue(new Callback<List<EventHomeResponse>>() {
            @Override
            public void onResponse(Call<List<EventHomeResponse>> call, Response<List<EventHomeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userEvents = response.body();

                    List<String> eventNames = new ArrayList<>();
                    for (EventHomeResponse event : userEvents) {
                        eventNames.add(event.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, eventNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    eventDropdown.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventHomeResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}