package com.example.eventplanner.fragments.event.create_event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.R;

import java.util.ArrayList;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private ArrayList<ActivityItem> activitiesList;

    public AgendaAdapter(ArrayList<ActivityItem> activitiesList) {
        this.activitiesList = activitiesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityItem activity = activitiesList.get(position);
        holder.editTextName.setText(activity.getName());
        holder.editTextDescription.setText(activity.getDescription());
        holder.editTextLocation.setText(activity.getLocation());
    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText editTextName, editTextDescription, editTextLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextName = itemView.findViewById(R.id.editTextName);
            editTextDescription = itemView.findViewById(R.id.editTextDescription);
            editTextLocation = itemView.findViewById(R.id.editTextLocation);
        }
    }
}