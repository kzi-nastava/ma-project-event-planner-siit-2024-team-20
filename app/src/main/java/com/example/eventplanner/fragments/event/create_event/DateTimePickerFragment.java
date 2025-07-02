package com.example.eventplanner.fragments.event.create_event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventplanner.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;

public class DateTimePickerFragment extends Fragment {

    private Button datePickerButton, timePickerButton;
    private Button endDatePickerButton, endTimePickerButton;

    private LocalDate startDate, endDate;
    private LocalTime startTime, endTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_time_picker, container, false);

        datePickerButton = view.findViewById(R.id.datePickerButton);
        timePickerButton = view.findViewById(R.id.timePickerButton);
        endDatePickerButton = view.findViewById(R.id.endDatePickerButton);
        endTimePickerButton = view.findViewById(R.id.endTimePickerButton);

        startDate = LocalDate.now();
        endDate = LocalDate.now();
        startTime = LocalTime.now().withSecond(0).withNano(0);
        endTime = LocalTime.now().withSecond(0).withNano(0);

        updateDateButtonText();
        updateTimeButtonText();
        updateEndDateButtonText();
        updateEndTimeButtonText();

        datePickerButton.setOnClickListener(v -> openStartDatePicker());
        timePickerButton.setOnClickListener(v -> openStartTimePicker());
        endDatePickerButton.setOnClickListener(v -> openEndDatePicker());
        endTimePickerButton.setOnClickListener(v -> openEndTimePicker());

        return view;
    }

    private void updateDateButtonText() {
        datePickerButton.setText(startDate.toString());
    }

    private void updateTimeButtonText() {
        timePickerButton.setText(startTime.toString());
    }

    private void updateEndDateButtonText() {
        endDatePickerButton.setText(endDate.toString());
    }

    private void updateEndTimeButtonText() {
        endTimePickerButton.setText(endTime.toString());
    }

    public void openStartDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    startDate = LocalDate.of(year, month + 1, dayOfMonth);
                    updateDateButtonText();
                }, startDate.getYear(), startDate.getMonthValue() - 1, startDate.getDayOfMonth());
        datePickerDialog.show();
    }

    public void openStartTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                (view, hourOfDay, minute) -> {
                    startTime = LocalTime.of(hourOfDay, minute);
                    updateTimeButtonText();
                }, startTime.getHour(), startTime.getMinute(), true);
        timePickerDialog.show();
    }

    public void openEndDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    endDate = LocalDate.of(year, month + 1, dayOfMonth);
                    updateEndDateButtonText();
                }, endDate.getYear(), endDate.getMonthValue() - 1, endDate.getDayOfMonth());
        datePickerDialog.show();
    }

    public void openEndTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                (view, hourOfDay, minute) -> {
                    endTime = LocalTime.of(hourOfDay, minute);
                    updateEndTimeButtonText();
                }, endTime.getHour(), endTime.getMinute(), true);
        timePickerDialog.show();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}