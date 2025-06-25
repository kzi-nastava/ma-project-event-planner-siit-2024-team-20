package com.example.eventplanner.activities.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.fragments.event.AddToFavouritesEventFragment;
import com.example.eventplanner.fragments.event.AdditionalInformationFragment;
import com.example.eventplanner.helpers.EventPdfGenerator;
import com.example.eventplanner.model.eventCreation.AgendaCreationRequest;
import com.example.eventplanner.model.eventPage.AgendaResponse;
import com.example.eventplanner.model.eventPage.EventDisplayResponse;
import com.example.eventplanner.model.homepage.EventHomeResponse;
import com.example.eventplanner.services.IEventService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;
import com.google.gson.Gson;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends AppCompatActivity{
    private TextView title, description, dateText, timeText, organizerText, locationText, typeText, guestsText;
    private TableLayout agendaTable;

    private IEventService eventService;

    private EventDisplayResponse lastEvent;
    private TextView agendaTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        initViews();
        eventService = ApiService.getEventService();
        Long eventId = getIntent().getLongExtra("event_id", -1);
        if (eventId != -1) {
            fetchEventDetails(eventId);
        } else {
            Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (eventId != -1) {
            AddToFavouritesEventFragment favFragment = new AddToFavouritesEventFragment();
            Bundle args = new Bundle();
            args.putLong("event_id", eventId);
            favFragment.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.add_to_favourites_container, favFragment)
                    .commit();
        } else {
            Toast.makeText(this, "Invalid event ID", Toast.LENGTH_SHORT).show();
        }
        String role = AuthService.getRoleFromToken();

        if (eventId != -1 && role != null && (role.equals("ROLE_ADMIN") || role.equals("ROLE_EVENT_ORGANIZER"))) {
            AdditionalInformationFragment additionalFragment = new AdditionalInformationFragment();
            Bundle args = new Bundle();
            args.putLong("event_id", eventId);
            additionalFragment.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.additional_info_fragment_container, additionalFragment)
                    .commit();
        }



    }

    private void initViews() {
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        dateText = findViewById(R.id.event_date_text);
        timeText = findViewById(R.id.event_time_text);
        organizerText = findViewById(R.id.event_organizer_text);
        locationText = findViewById(R.id.event_location_text);
        typeText = findViewById(R.id.event_type_text);
        guestsText = findViewById(R.id.event_guests_text);
        agendaTable = findViewById(R.id.agenda_table);
        agendaTitle = findViewById(R.id.agenda_title);

        ImageView backBtn = findViewById(R.id.return_back);
        backBtn.setOnClickListener(v -> finish());
        LinearLayout pdfDownloadButton = findViewById(R.id.pdf_download_button);
        pdfDownloadButton.setOnClickListener(v -> {
            if (lastEvent != null) {
                EventPdfGenerator pdfGenerator = new EventPdfGenerator(this);
                pdfGenerator.generatePdf(lastEvent);
            } else {
                Toast.makeText(this, "Event data is not loaded yet.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchEventDetails(Long eventId) {
        eventService.getEvent(eventId).enqueue(new Callback<EventDisplayResponse>() {
            @Override
            public void onResponse(Call<EventDisplayResponse> call, Response<EventDisplayResponse> response) {
                Log.d("API_RESPONSE", new Gson().toJson(response.body()));
                if (response.isSuccessful() && response.body() != null) {
                    populateUI(response.body());
                } else {
                    Toast.makeText(EventDetailsActivity.this, "Failed to load event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventDisplayResponse> call, Throwable t) {
                Toast.makeText(EventDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUI(EventDisplayResponse event) {
        this.lastEvent = event;
        title.setText(event.getName());
        description.setText(event.getDescription());
        dateText.setText(event.getStartDate() + " - " + event.getEndDate());
        timeText.setText(event.getStartTime() + " - " + event.getEndTime());
        organizerText.setText(event.getOrganizer().getEmail());
        locationText.setText(event.getAddress());
        typeText.setText(event.isPrivate() ? "Private" : "Public");
        guestsText.setText(String.valueOf(event.getGuests()));

        populateAgendaTable(event.getAgenda());
    }

    private void populateAgendaTable(List<AgendaResponse> agenda) {
        if (agenda == null || agenda.isEmpty()) {
            agendaTable.setVisibility(View.GONE);
            if (agendaTitle != null) {
                agendaTitle.setVisibility(View.GONE);
            }
            return;

        }

        agendaTable.setVisibility(View.VISIBLE);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (AgendaResponse item : agenda) {
            TableRow row = new TableRow(this);

            TextView name = createAgendaCell(item.getName());

            LocalTime start = item.getFromTime();
            LocalTime end = item.getToTime();

            String fromFormatted = start != null ? start.format(timeFormatter) : "N/A";
            String toFormatted = end != null ? end.format(timeFormatter) : "N/A";

            TextView from = createAgendaCell(fromFormatted);
            TextView to = createAgendaCell(toFormatted);
            TextView location = createAgendaCell(item.getLocation());
            TextView desc = createAgendaCell(item.getDescription());


            row.addView(name);
            row.addView(from);
            row.addView(to);
            row.addView(location);
            row.addView(desc);

            agendaTable.addView(row);
        }
    }

    private TextView createAgendaCell(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(10, 10, 10, 10);
        tv.setTextColor(getResources().getColor(R.color.black));
        return tv;
    }



}