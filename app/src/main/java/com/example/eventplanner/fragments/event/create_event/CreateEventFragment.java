package com.example.eventplanner.fragments.event.create_event;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.model.entities.EventType;
import com.example.eventplanner.model.eventCreation.AgendaCreationRequest;
import com.example.eventplanner.model.eventCreation.EventCreationRequest;
import com.example.eventplanner.services.IEventService;
import com.example.eventplanner.services.IEventTypeService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventFragment extends Fragment {

    private AutoCompleteTextView autoCompleteTextView;
    private List<EventType> eventTypes = new ArrayList<>();
    private SuggestedCategoriesFragment suggestedCategoriesFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        autoCompleteTextView = view.findViewById(R.id.auto_complete_txt);

        suggestedCategoriesFragment = (SuggestedCategoriesFragment)
                getChildFragmentManager().findFragmentById(R.id.fragmentContainerView3);

        fetchEventTypes();
        Button createEventButton = view.findViewById(R.id.button_create_event);
        EditText numGuestsEditText = view.findViewById(R.id.editTextNumber);
        Switch privateSwitch = view.findViewById(R.id.switch1);
        LinearLayout emailInputContainer = view.findViewById(R.id.emailInputContainer);
        Button btnAddEmail = view.findViewById(R.id.btnAddEmail);
        privateSwitch.setEnabled(false); // ne može da se čekira dok nema gostiju
        emailInputContainer.setVisibility(View.GONE);
        btnAddEmail.setVisibility(View.GONE);
        numGuestsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int numGuests = Integer.parseInt(s.toString().trim());
                    privateSwitch.setEnabled(numGuests > 0);
                    if (numGuests == 0) {
                        privateSwitch.setChecked(false); // resetuj ako je broj nula
                        emailInputContainer.setVisibility(View.GONE);
                        btnAddEmail.setVisibility(View.GONE);
                    }
                } catch (NumberFormatException e) {
                    privateSwitch.setEnabled(false);
                    privateSwitch.setChecked(false);
                    emailInputContainer.setVisibility(View.GONE);
                    btnAddEmail.setVisibility(View.GONE);
                }
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
        privateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                emailInputContainer.setVisibility(View.VISIBLE);
                btnAddEmail.setVisibility(View.VISIBLE);
            } else {
                emailInputContainer.setVisibility(View.GONE);
                btnAddEmail.setVisibility(View.GONE);
            }
        });
        btnAddEmail.setOnClickListener(v -> {
            int childCount = emailInputContainer.getChildCount();

            // Ako već postoji bar jedno polje, proveri poslednje uneto
            if (childCount > 0) {
                View lastChild = emailInputContainer.getChildAt(childCount - 1);
                if (lastChild instanceof EditText) {
                    String email = ((EditText) lastChild).getText().toString().trim();
                    Pattern emailPattern = Patterns.EMAIL_ADDRESS;

                    if (email.isEmpty()) {
                        Toast.makeText(getContext(), "Please enter an email before adding another.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!emailPattern.matcher(email).matches()) {
                        Toast.makeText(getContext(), "Invalid email: " + email, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }

            // Ako je prethodni email validan (ili je prvo polje), dodaj novo
            EditText newEmail = new EditText(getContext());
            newEmail.setHint("E-mail");
            newEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 16, 0, 0);
            newEmail.setLayoutParams(params);

            emailInputContainer.addView(newEmail);
        });


        createEventButton.setOnClickListener(v -> createEvent());


        return view;
    }

    private void fetchEventTypes() {
        IEventTypeService service = ApiService.getEventTypeService();
        service.getAllEventTypes().enqueue(new Callback<List<EventType>>() {
            @Override
            public void onResponse(@NonNull Call<List<EventType>> call, @NonNull Response<List<EventType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventTypes = response.body();

                    List<String> eventTypeNames = new ArrayList<>();
                    eventTypeNames.add("All");

                    for (EventType type : eventTypes) {
                        eventTypeNames.add(type.getName());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            eventTypeNames
                    );
                    autoCompleteTextView.setAdapter(adapter);

                    setupDropdownSelection();
                } else {
                    Toast.makeText(getContext(), "Failed to load event types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<EventType>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupDropdownSelection() {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = (String) parent.getItemAtPosition(position);

            if (selectedName.equals("All")) {
                if (suggestedCategoriesFragment != null) {
                    suggestedCategoriesFragment.setCategories(List.of("None"));
                }
                return;
            }

            for (EventType type : eventTypes) {
                if (type.getName().equals(selectedName)) {
                    fetchSuggestedCategories(type.getId());
                    break;
                }
            }
        });
    }

    private void fetchSuggestedCategories(Long eventTypeId) {
        IEventService service = ApiService.getEventService();
        service.getSuggestedCategoriesForType(eventTypeId).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> categories = response.body();
                    if (categories.isEmpty()) {
                        categories.add("None");
                    }

                    if (suggestedCategoriesFragment != null) {
                        suggestedCategoriesFragment.setCategories(categories);
                    }
                } else {
                    showNoneFallback();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                showNoneFallback();
            }

            private void showNoneFallback() {
                if (suggestedCategoriesFragment != null) {
                    suggestedCategoriesFragment.setCategories(List.of("None"));
                }
            }
        });
    }
    private double[] getLocationFromAddress(String city, String address, String num) {
        String fullAddress = address + " " + num + ", " + city;
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> addresses = geocoder.getFromLocationName(fullAddress, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                return new double[]{location.getLongitude(), location.getLatitude()};
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to get location: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return new double[]{0.0, 0.0};
    }

    private void createEvent() {
        DateTimePickerFragment dateTimePickerFragment = (DateTimePickerFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragmentContainerView4);

        if (dateTimePickerFragment == null) {
            Toast.makeText(getContext(), "Date/time picker not found", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDate startDate = dateTimePickerFragment.getStartDate();
        LocalTime startTime = dateTimePickerFragment.getStartTime();
        LocalDate endDate = dateTimePickerFragment.getEndDate();
        LocalTime endTime = dateTimePickerFragment.getEndTime();

        if (endDate.isBefore(startDate) || (endDate.isEqual(startDate) && endTime.isBefore(startTime))) {
            Toast.makeText(getContext(), "End date/time must be after start date/time", Toast.LENGTH_LONG).show();
            return;
        }

        String selectedTypeName = autoCompleteTextView.getText().toString();
        Long eventTypeId = null;
        if (!selectedTypeName.equals("All")) {
            for (EventType type : eventTypes) {
                if (type.getName().equals(selectedTypeName)) {
                    eventTypeId = type.getId();
                    break;
                }
            }
            if (eventTypeId == null) {
                Toast.makeText(getContext(), "Please select a valid event type", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            eventTypeId = null;
        }

        String name = ((EditText) getView().findViewById(R.id.editTextText10)).getText().toString();
        String description = ((EditText) getView().findViewById(R.id.editTextText11)).getText().toString();
        String city = ((EditText) getView().findViewById(R.id.editTextText14)).getText().toString();
        String address = ((EditText) getView().findViewById(R.id.editTextText12)).getText().toString();
        String num = ((EditText) getView().findViewById(R.id.editTextText13)).getText().toString();
        int numOfGuests = Integer.parseInt(((EditText) getView().findViewById(R.id.editTextNumber)).getText().toString());
        boolean isPrivate = ((Switch) getView().findViewById(R.id.switch1)).isChecked();

        List<String> guestEmails = new ArrayList<>();

        double[] location = getLocationFromAddress(city, address, num);
        double longitude = location[0];
        double latitude = location[1];
        Long creatorId = (long) AuthService.getUserIdFromToken();

        List<AgendaCreationRequest> agenda = new ArrayList<>();
        CreateAgendaFragment agendaFragment = (CreateAgendaFragment)
                getChildFragmentManager().findFragmentById(R.id.fragmentContainerView5);
        if (agendaFragment != null) {
            agenda = agendaFragment.getAgendaItems();
        }

        if (!validateAgendaWithinEventTime(startTime, endTime, agenda)) {
            Toast.makeText(getContext(), "\n" +
                    "The agenda items are incorrect.", Toast.LENGTH_LONG).show();
            return;
        }
        guestEmails.clear(); // ako već postoji inicijalizovana lista

        LinearLayout emailContainer = getView().findViewById(R.id.emailInputContainer);
        for (int i = 0; i < emailContainer.getChildCount(); i++) {
            View child = emailContainer.getChildAt(i);
            if (child instanceof EditText) {
                String email = ((EditText) child).getText().toString().trim();
                if (!email.isEmpty()) {
                    guestEmails.add(email);
                }
            }
        }
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        for (String email : guestEmails) {
            if (!emailPattern.matcher(email).matches()) {
                Toast.makeText(getContext(), "Invalid email: " + email, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        EventCreationRequest eventRequest = new EventCreationRequest(
                name, description, city, address, num,
                numOfGuests, isPrivate, guestEmails,
                startDate, startTime, endDate, endTime,
                eventTypeId, longitude, latitude, creatorId,
                agenda
        );

        IEventService eventService = ApiService.getEventService();
        eventService.createEvent(eventRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Event created successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Failed to create event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private boolean validateAgendaWithinEventTime(LocalTime eventStart, LocalTime eventEnd, List<AgendaCreationRequest> agendaList) {
        for (AgendaCreationRequest item : agendaList) {
            if (item.getFrom().isBefore(eventStart) || item.getTo().isAfter(eventEnd)) {
                return false;
            }

            for (AgendaCreationRequest other : agendaList) {
                if (item == other) continue;

                boolean overlaps = item.getFrom().isBefore(other.getTo()) && other.getFrom().isBefore(item.getTo());
                if (overlaps) {
                    return false;
                }
            }
        }

        return true;
    }



}
