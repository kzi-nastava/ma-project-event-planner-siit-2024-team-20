package com.example.eventplanner.fragments.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.adapters.NotificationAdapter;
import com.example.eventplanner.model.entities.Notification;
import com.example.eventplanner.model.notification.NotificationResponse;
import com.example.eventplanner.model.notification.NotificationSettingsResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationResponse> notifications = new ArrayList<>();
    private Button muteAllBtn;
    private Button closeBtn;
    private Boolean allMuted = null;

    private Long userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        userId= (long) AuthService.getUserIdFromToken();
        recyclerView = view.findViewById(R.id.recycler_notifications);
        muteAllBtn = view.findViewById(R.id.button_mute_all);

        adapter = new NotificationAdapter(notifications, notification -> markAsRead(notification));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadNotifications();
        loadMuteStatus();

        muteAllBtn.setOnClickListener(v -> {
            allMuted = allMuted == null ? true : !allMuted;
            setMuteStatus(allMuted);
            updateMuteButton();
        });


    }

    private void loadNotifications() {
        ApiService.getNotificationService().getUserNotifications(userId).enqueue(new Callback<List<NotificationResponse>>() {
            @Override
            public void onResponse(Call<List<NotificationResponse>> call, Response<List<NotificationResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    notifications.clear();
                    notifications.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Error loading notifications", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NotificationResponse>> call, Throwable t) {
                t.printStackTrace();
                Log.d("HEEEJ", "API error: " + t.getMessage());
                Toast.makeText(getContext(), "API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadMuteStatus() {
        ApiService.getNotificationService().getMuteStatus(userId).enqueue(new Callback<NotificationSettingsResponse>() {
            @Override
            public void onResponse(Call<NotificationSettingsResponse> call, Response<NotificationSettingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allMuted = response.body().isNotificationsMuted();
                    muteAllBtn.setVisibility(View.VISIBLE);
                    updateMuteButton();
                }
            }

            @Override
            public void onFailure(Call<NotificationSettingsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void updateMuteButton() {
        if (allMuted != null && allMuted) {
            muteAllBtn.setText("Unmute All");
        } else {
            muteAllBtn.setText("Mute All");
        }
    }

    private void setMuteStatus(boolean mute) {
        NotificationSettingsResponse dto = new NotificationSettingsResponse(mute);
        ApiService.getNotificationService().muteNotifications(userId, dto).enqueue(new Callback<NotificationSettingsResponse>() {
            @Override
            public void onResponse(Call<NotificationSettingsResponse> call, Response<NotificationSettingsResponse> response) {
                if (response.isSuccessful()) {
                    allMuted = response.body().isNotificationsMuted();
                    updateMuteButton();
                    Toast.makeText(getContext(), mute ? "Notifications muted" : "Notifications unmuted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationSettingsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void markAsRead(NotificationResponse notification) {
        if (notification.isRead()) return;

        ApiService.getNotificationService().markNotificationAsRead(notification.getId()).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    notification.setRead(true);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Marked as read", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
