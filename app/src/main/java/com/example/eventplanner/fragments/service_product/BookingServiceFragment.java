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
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.eventplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingServiceFragment extends Fragment {


    public BookingServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingServiceFragment newInstance(String param1, String param2) {
        BookingServiceFragment fragment = new BookingServiceFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillDropdown(view);
    }
    private void fillDropdown(View view) {
        Spinner eventSpinner = view.findViewById(R.id.event_spinner);

        List<String> events = Arrays.asList("Event 1", "Event 2", "Event 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, events);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(adapter);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedEvent = events.get(position);
                // Uradi nešto sa selektovanim događajem
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ako ništa nije izabrano
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout za fragment
        View view = inflater.inflate(R.layout.fragment_booking_service, container, false);

        // Inicijalizacija "From" NumberPicker-a
        NumberPicker fromHourPicker = view.findViewById(R.id.fromHourPicker);
        NumberPicker fromMinutePicker = view.findViewById(R.id.fromMinutePicker);

        // Inicijalizacija "To" NumberPicker-a
        NumberPicker toHourPicker = view.findViewById(R.id.toHourPicker);
        NumberPicker toMinutePicker = view.findViewById(R.id.toMinutePicker);

        // Podešavanje opsega za sate i minute
        setupNumberPicker(fromHourPicker, 0, 23);
        setupNumberPicker(fromMinutePicker, 0, 59);
        setupNumberPicker(toHourPicker, 0, 23);
        setupNumberPicker(toMinutePicker, 0, 59);

        // Listener za promene vrednosti (opciono)
        fromHourPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Logika za "From Hour"
        });

        toMinutePicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Logika za "To Minute"
        });

        return view;
    }

    // Pomoćna metoda za postavljanje NumberPicker-a
    private void setupNumberPicker(NumberPicker picker, int min, int max) {
        picker.setMinValue(min);
        picker.setMaxValue(max);
        picker.setWrapSelectorWheel(true);
    }
}