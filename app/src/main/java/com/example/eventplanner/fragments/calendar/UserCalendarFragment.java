package com.example.eventplanner.fragments.calendar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.helpers.EventDecorator;
import com.example.eventplanner.model.eventPage.EventDisplayResponse;
import com.example.eventplanner.services.IUserService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserCalendarFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private IUserService userService;
    private int userId = AuthService.getUserIdFromToken();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Map<CalendarDay, EventDisplayResponse> eventMap = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        userService = ApiService.getUserService();

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            if (eventMap.containsKey(date)) {
                EventDisplayResponse event = eventMap.get(date);
                Log.d("UserCalendarFragment", "Kliknuto na događaj: " + event.getName() + ", ID: " + event.getId());
                openEventDetails(event);
            } else {
                Log.d("UserCalendarFragment", "Kliknuto na datum bez događaja: " + date.toString());
            }
        });


        fetchUserEvents();

        return view;
    }

    private void fetchUserEvents() {
        Call<List<EventDisplayResponse>> call = userService.getCalendar(userId);
        call.enqueue(new Callback<List<EventDisplayResponse>>() {
            @Override
            public void onResponse(Call<List<EventDisplayResponse>> call, Response<List<EventDisplayResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EventDisplayResponse> events = response.body();
                    markEventDates(events);
                } else {
                    Log.e("UserCalendarFragment", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<EventDisplayResponse>> call, Throwable t) {
                Log.e("UserCalendarFragment", "API call failed", t);
            }
        });
    }

    private void markEventDates(List<EventDisplayResponse> events) {
        eventMap.clear();
        for (EventDisplayResponse event : events) {
            try {
                Date date = dateFormat.parse(event.getStartDate());
                if (date != null) {
                    CalendarDay day = CalendarDay.from(date);
                    eventMap.put(day, event);
                    calendarView.addDecorator(new EventDecorator(day));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void openEventDetails(EventDisplayResponse event) {

        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra("event_id", event.getId());
        startActivity(intent);
    }
}