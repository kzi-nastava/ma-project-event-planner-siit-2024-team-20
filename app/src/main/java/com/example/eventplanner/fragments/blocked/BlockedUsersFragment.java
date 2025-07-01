package com.example.eventplanner.fragments.blocked;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.adapters.BlockedUsersAdapter;
import com.example.eventplanner.model.users.UserResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockedUsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private BlockedUsersAdapter adapter;
    private List<UserResponse> blockedUsers = new ArrayList<>();
    private Long userId; // Uzmi iz tokena ili AuthService

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blocked_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.blockedUsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BlockedUsersAdapter(blockedUsers, this::unblockUser);
        recyclerView.setAdapter(adapter);

        userId =(long)AuthService.getUserIdFromToken();
        fetchBlockedUsers();
    }

    private void fetchBlockedUsers() {
        ApiService.getUserService().getBlockedUsers(userId).enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    blockedUsers.clear();
                    blockedUsers.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load blocked users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unblockUser(UserResponse user) {
        ApiService.getUserService().unblockUser(userId, user.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                blockedUsers.remove(user);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Unblocked " + user.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to unblock", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
