package com.example.eventplanner.activities.service_product;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fillDropdown();
        ImageView returnBack = findViewById(R.id.return_back);
        returnBack.setOnClickListener(v -> onBackPressed());

        EditText timeFrom = findViewById(R.id.time_from);
        EditText timeTo = findViewById(R.id.time_to);

        timeFrom.setOnEditorActionListener((v, actionId, event) -> {
            String timeFromText = timeFrom.getText().toString();
            if (!timeFromText.isEmpty()) {
                // Pretvori vreme u 24h format (npr. "15:00")
                String timeToCalculated = calculateEndTime(timeFromText);
                timeTo.setText(timeToCalculated);
            }
            return false;
        });

    }
    private String calculateEndTime(String timeFromText) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date startTime = sdf.parse(timeFromText);
            long endTimeMillis = startTime.getTime() + (2 * 60 * 60 * 1000); // Dodaj 2 sata
            return sdf.format(new Date(endTimeMillis));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void fillDropdown(){
        Spinner eventSpinner = findViewById(R.id.event_spinner);

        List<String> events = Arrays.asList("Event 1", "Event 2", "Event 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, events);
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
}