package com.example.eventplanner.fragments.notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.adapters.NotificationAdapter;
import com.example.eventplanner.model.Notification;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<Notification> notificationList;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        // Inicijalizacija RecyclerView-a
        RecyclerView recyclerView = rootView.findViewById(R.id.notificationsRecyclerView);

        // Postavi LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Napravite adapter za RecyclerView
        NotificationAdapter adapter = new NotificationAdapter(getSampleNotifications());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private List<Notification> getSampleNotifications() {
        return List.of(
                new Notification(1L, "Notification 1", LocalDateTime.now(), false, "Item 1"),
                new Notification(2L, "Notification 2", LocalDateTime.now(), false, "Item 2")
        );
    }
}
