package com.example.eventplanner.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.model.entities.Notification;
import com.example.eventplanner.R;
import com.example.eventplanner.model.notification.NotificationResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    public interface OnNotificationClickListener {
        void onNotificationClick(NotificationResponse notification);
    }

    private List<NotificationResponse> notifications;
    private OnNotificationClickListener listener;

    public NotificationAdapter(List<NotificationResponse> notifications, OnNotificationClickListener listener) {
        this.notifications = notifications;
        this.listener = listener;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView typeText, contentText, timeText;
        View container;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.text_type);
            contentText = itemView.findViewById(R.id.text_content);
            timeText = itemView.findViewById(R.id.text_time);
            container = itemView.findViewById(R.id.notification_card);
        }
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        NotificationResponse notification = notifications.get(position);
        holder.typeText.setText(notification.getType());
        holder.contentText.setText(notification.getContent());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTime = notification.getTimeOfSending().format(formatter);
        holder.timeText.setText(formattedTime);

        if (notification.isRead()) {
            holder.typeText.setAlpha(0.5f);
            holder.contentText.setAlpha(0.5f);
            holder.timeText.setAlpha(0.5f);

            holder.typeText.setTypeface(null, Typeface.NORMAL);
            holder.contentText.setTypeface(null, Typeface.NORMAL);
        } else {
            holder.typeText.setAlpha(1.0f);
            holder.contentText.setAlpha(1.0f);
            holder.timeText.setAlpha(1.0f);

            holder.typeText.setTypeface(null, Typeface.BOLD);
            holder.contentText.setTypeface(null, Typeface.BOLD);
        }


        holder.container.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNotificationClick(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
