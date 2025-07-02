package com.example.eventplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.users.UserResponse;

import java.util.List;
import java.util.function.Consumer;

public class BlockedUsersAdapter extends RecyclerView.Adapter<BlockedUsersAdapter.BlockedUserViewHolder> {

    private List<UserResponse> blockedUsers;
    private Consumer<UserResponse> unblockCallback;

    public BlockedUsersAdapter(List<UserResponse> blockedUsers, Consumer<UserResponse> unblockCallback) {
        this.blockedUsers = blockedUsers;
        this.unblockCallback = unblockCallback;
    }

    @NonNull
    @Override
    public BlockedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blocked_user_item, parent, false);
        return new BlockedUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockedUserViewHolder holder, int position) {
        UserResponse user = blockedUsers.get(position);
        holder.name.setText(user.getName() + " " + user.getLastName());

        holder.unblockButton.setOnClickListener(v -> unblockCallback.accept(user));
    }

    @Override
    public int getItemCount() {
        return blockedUsers.size();
    }

    static class BlockedUserViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Button unblockButton;

        public BlockedUserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.blockedUserName);
            unblockButton = itemView.findViewById(R.id.unblockButton);
        }
    }
}

