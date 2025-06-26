package com.example.eventplanner.fragments.service_product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.eventplanner.model.eventDetails.EventDetailsResponse;
import com.example.eventplanner.model.homepage.EventHomeResponse;
import com.example.eventplanner.model.productDetails.ServiceDetailsResponse;
import com.example.eventplanner.model.serviceReservation.ServiceBookingRequest;
import com.example.eventplanner.services.spec.ApiService;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private EventHomeResponse selectedEvent;

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
        setupDropdownListeners();
        fetchUserEvents();
        // Submit button
        submitButton.setOnClickListener(v -> {
            if (validateReservation()) {
                fetchServiceAndBook(serviceId);
            }
        });

    }

    private void setupDropdownListeners() {
        eventDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (userEvents != null && !userEvents.isEmpty() && position < userEvents.size()) {
                    selectedEvent = userEvents.get(position);
                    if (selectedEvent != null) {
                        try {
                            List<String> dates = generateDatesBetween(selectedEvent.getStartDate(), selectedEvent.getEndDate());
                            Log.d("HEJ", selectedEvent.getStartDate() + " " + selectedEvent.getEndDate());
                            ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dates);
                            dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dateDropdown.setAdapter(dateAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private List<String> generateDatesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate start = startDateTime.toLocalDate();
        LocalDate end = endDateTime.toLocalDate();

        while (!start.isAfter(end)) {
            dates.add(start.format(formatter));
            start = start.plusDays(1);
        }

        return dates;
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
                Log.d("BookingFragment", "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    userEvents = response.body();
                    Log.d("BookingFragment", "Fetched events count: " + userEvents.size());
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

    private void fetchServiceAndBook(Long serviceId) {
        ApiService.getServiceService().getServiceDetails(serviceId).enqueue(new Callback<ServiceDetailsResponse>() {
            @Override
            public void onResponse(Call<ServiceDetailsResponse> call, Response<ServiceDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ServiceDetailsResponse service = response.body();
                    createAndSendBookingRequest(service);

                } else {
                    Toast.makeText(getContext(), "Failed to load service details.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error fetching service details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAndSendBookingRequest(ServiceDetailsResponse service){

    int fromHour = fromHourPicker.getValue();
    int fromMinute = fromMinutePicker.getValue();
    int toHour = toHourPicker.getValue();
    int toMinute = toMinutePicker.getValue();

    // 1. Datum iz dropdown-a
    String selectedDateStr = (String) dateDropdown.getSelectedItem();
    if(selectedDateStr ==null||selectedDateStr.isEmpty())

    {
        Toast.makeText(getContext(), "Please select a date.", Toast.LENGTH_SHORT).show();
        return;
    }

    // 2. Parsiranje LocalDate
    LocalDate date = LocalDate.parse(selectedDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    LocalTime startTime = LocalTime.of(fromHour, fromMinute);
    LocalTime endTime = LocalTime.of(toHour, toMinute);

    if(startTime ==null||endTime ==null)

    {
        Toast.makeText(getContext(), "Start time or end time cannot be null", Toast.LENGTH_SHORT).show();
        return;
    }

    // 3. Dohvati eventId iz liste
    int selectedEventPosition = eventDropdown.getSelectedItemPosition();
if(selectedEventPosition< 0||selectedEventPosition >=userEvents.size())

    {
        Toast.makeText(getContext(), "Invalid event selected.", Toast.LENGTH_SHORT).show();
        return;
    }

    // Uzmi event sa pozicije iz liste
    Long eventId = selectedEvent.getId();

    // 4. Kreiraj DTO
    ServiceBookingRequest request = new ServiceBookingRequest();
    request.setDate(date);
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    request.setEventId(eventId);
    request.setService(service);

    Log.d("HEJ","EventId: "+request.getEventId());

    // 5. Pozovi backend
    ApiService.getServiceService()
            // Ovde treba da menjaš generički tip u Callback<ServiceBookingDTO> ili kakav ti je DTO za odgovor
            .

    bookService(serviceId, request)
    .

    enqueue(new Callback<ServiceBookingRequest>() {  // <-- ovde je bitno da ti je DTO za odgovor
        @Override
        public void onResponse
        (Call < ServiceBookingRequest> call, Response < ServiceBookingRequest> response){
            if (response.isSuccessful()) {
                Toast.makeText(getContext(), "Booking successful!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    String errorBody = response.errorBody().string();
                    JSONObject jsonObject = new JSONObject(errorBody);
                    String message = jsonObject.optString("message", "Booking failed.");
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Booking failed.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure (Call < ServiceBookingRequest> call, Throwable t){
            Log.e("BOOKING_DEBUG", "Booking request failed", t);
            Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    });
}




}