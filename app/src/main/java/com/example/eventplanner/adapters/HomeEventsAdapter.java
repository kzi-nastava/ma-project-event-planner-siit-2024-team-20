package com.example.eventplanner.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.EventHome;
import com.example.eventplanner.R;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeEventsAdapter extends RecyclerView.Adapter<HomeEventsAdapter.EventViewHolder> {

    private List<EventHome> eventsList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(EventHome event);
    }

    public HomeEventsAdapter(List<EventHome> eventsList, OnItemClickListener listener) {
        this.eventsList = eventsList;
        this.listener = listener;
    }
    public void updateData(List<EventHome> newEvents) {
        this.eventsList.clear();
        this.eventsList.addAll(newEvents);
        notifyDataSetChanged();
    }
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        EventHome event = eventsList.get(position);
        holder.eventName.setText(event.getName());
        holder.eventDescription.setText(event.getDescription());
        holder.eventType.setText(event.getEventType());
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        String formattedDate = event.getStartDate().format(displayFormatter);
        holder.eventDate.setText(formattedDate);
        holder.eventLocation.setText(event.getLocation());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(event));
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventName, eventDescription, eventType, eventDate, eventLocation;
        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventType = itemView.findViewById(R.id.event_type);
            eventDate = itemView.findViewById(R.id.event_date);
            eventLocation = itemView.findViewById(R.id.event_location);
        }
    }
}

