package com.example.eventplanner.fragments.event.create_event;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eventplanner.R;
import com.example.eventplanner.model.eventCreation.AgendaCreationRequest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CreateAgendaFragment extends Fragment {

    private View rootView;
    private LocalTime fromTime;
    private LocalTime toTime;
    private List<AgendaCreationRequest> agendaItems = new ArrayList<>();
    private TableLayout agendaTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_agenda, container, false);

        agendaTable = rootView.findViewById(R.id.agenda_table);
        Button timeFromButton = rootView.findViewById(R.id.timePickerButtonA);
        Button timeToButton = rootView.findViewById(R.id.timePickerButtonA2);
        Button addButton = rootView.findViewById(R.id.button);

        timeFromButton.setOnClickListener(v -> pickTime(true));
        timeToButton.setOnClickListener(v -> pickTime(false));
        addButton.setOnClickListener(v -> addAgendaItem());

        return rootView;
    }

    private void pickTime(boolean isStart) {
        TimePickerDialog.OnTimeSetListener listener = (view, hourOfDay, minute) -> {
            LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
            if (isStart) {
                fromTime = selectedTime;
                Button timeFromButton = rootView.findViewById(R.id.timePickerButtonA);
                timeFromButton.setText(selectedTime.toString());
            } else {
                toTime = selectedTime;
                Button timeToButton = rootView.findViewById(R.id.timePickerButtonA2);
                timeToButton.setText(selectedTime.toString());
            }
        };

        LocalTime now = LocalTime.now();
        new TimePickerDialog(getContext(), listener, now.getHour(), now.getMinute(), true).show();
    }


    private void addAgendaItem() {
        EditText nameInput = rootView.findViewById(R.id.editTextText14);
        EditText descInput = rootView.findViewById(R.id.editTextText15);
        EditText locationInput = rootView.findViewById(R.id.editTextText16);

        String name = nameInput.getText().toString().trim();
        String description = descInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || location.isEmpty() || fromTime == null || toTime == null) {
            Toast.makeText(getContext(), "Please fill in all fields and select both times", Toast.LENGTH_SHORT).show();
            return;
        }

        AgendaCreationRequest item = new AgendaCreationRequest(name, description, fromTime, toTime, location);
        agendaItems.add(item);

        TableRow row = new TableRow(getContext());
        row.addView(makeTextView(name));
        row.addView(makeTextView(description));
        row.addView(makeTextView(fromTime.toString()));
        row.addView(makeTextView(toTime.toString()));
        row.addView(makeTextView(location));
        agendaTable.addView(row);

        ImageButton deleteButton = new ImageButton(getContext());
        deleteButton.setImageResource(R.drawable.trash_solid_light);

        deleteButton.setPadding(10, 10, 10, 10);
        deleteButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        deleteButton.setOnClickListener(v -> {
            agendaTable.removeView(row);
            agendaItems.remove(item);
        });

        row.addView(deleteButton);


        nameInput.setText("");
        descInput.setText("");
        locationInput.setText("");
        fromTime = null;
        toTime = null;
    }

    private TextView makeTextView(String text) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setPadding(30, 10, 10, 10);
        tv.setTextColor(android.graphics.Color.parseColor("#f3eee9"));
        return tv;
    }

    public List<AgendaCreationRequest> getAgendaItems() {
        return agendaItems;
    }
}