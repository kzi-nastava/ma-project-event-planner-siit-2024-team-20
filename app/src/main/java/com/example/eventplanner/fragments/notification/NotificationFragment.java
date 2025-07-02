package com.example.eventplanner.fragments.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.adapters.NotificationAdapter;
import com.example.eventplanner.model.entities.Notification;
import com.example.eventplanner.model.notification.NotificationResponse;

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

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationResponse> notifications = new ArrayList<>();
    private Button muteAllBtn;
    private Button closeBtn;
    private Boolean allMuted = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_notifications);
        muteAllBtn = view.findViewById(R.id.button_mute_all);
        closeBtn = view.findViewById(R.id.button_close);

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

        closeBtn.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction().remove(NotificationFragment.this).commit();
        });
    }

    private void loadNotifications() {
        // TODO: pozovi API, sad simuliramo
        notifications.clear();
        adapter.notifyDataSetChanged();
    }

    private void loadMuteStatus() {
        // TODO: API poziv
        allMuted = false;
        muteAllBtn.setVisibility(View.VISIBLE);
        updateMuteButton();
    }

    private void updateMuteButton() {
        if (allMuted != null && allMuted) {
            muteAllBtn.setText("Unmute All");
        } else {
            muteAllBtn.setText("Mute All");
        }
    }

    private void setMuteStatus(boolean mute) {
        // TODO: pošalji mute status na backend
    }

    private void markAsRead(NotificationResponse notification) {
        if (notification.isRead()) return;

        // TODO: API poziv za označavanje kao pročitano
        notification.setRead(true);
        adapter.notifyDataSetChanged();
    }
}
