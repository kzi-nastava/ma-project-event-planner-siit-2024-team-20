package com.example.eventplanner.fragments.profile;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eventplanner.R;
import com.example.eventplanner.model.users.ReportUserRequest;
import com.example.eventplanner.model.users.UserViewResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {

    private ImageView profilePicture;
    private TextView userFullName, userEmail;
    private Button blockUserButton, reportUserButton;
    private Long viewedUserId;
    private ImageButton closeButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profilePicture = view.findViewById(R.id.profile_picture);
        userFullName = view.findViewById(R.id.user_full_name);
        userEmail = view.findViewById(R.id.user_email);
        blockUserButton = view.findViewById(R.id.block_user_button);
        reportUserButton = view.findViewById(R.id.report_user_button);
        closeButton = view.findViewById(R.id.close_button);
        Bundle bundle = getArguments();
        if (bundle != null) {
            viewedUserId = bundle.getLong("userId");
        }

        // Učitaj podatke
        loadUserProfile(viewedUserId);
        closeButton.setOnClickListener(v -> {
            // zatvori fragment popup
            requireActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });
        blockUserButton.setOnClickListener(v -> showBlockConfirmationDialog());
        reportUserButton.setOnClickListener(v -> showReportDialog());
    }

    private void loadUserProfile(Long userId) {
        Call<UserViewResponse> call = ApiService.getUserService().getUserById(userId);
        call.enqueue(new Callback<UserViewResponse>() {
            @Override
            public void onResponse(Call<UserViewResponse> call, Response<UserViewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserViewResponse user = response.body();
                    userFullName.setText(user.getName() + " " + user.getLastName());
                    userEmail.setText(user.getEmail());
                    Glide.with(requireContext())
                            .load(user.getProfileImage())
                            .placeholder(R.drawable.profile_icon)
                            .into(profilePicture);
                } else {
                    Toast.makeText(requireContext(), "Failed to load user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserViewResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBlockConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Block User")
                .setMessage("Are you sure you want to block this user?")
                .setPositiveButton("Block", (dialog, which) -> {
                    ApiService.getUserService().blockUser(viewedUserId).enqueue(new Callback<UserViewResponse>() {
                        @Override
                        public void onResponse(Call<UserViewResponse> call, Response<UserViewResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(requireContext(), "User blocked", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Failed to block user", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserViewResponse> call, Throwable t) {
                            Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showReportDialog() {
        final EditText input = new EditText(requireContext());
        input.setHint("Enter reason for report");

        new AlertDialog.Builder(requireContext())
                .setTitle("Report User")
                .setMessage("Please enter a reason for reporting this user:")
                .setView(input)
                .setPositiveButton("Report", (dialog, which) -> {
                    String reason = input.getText().toString().trim();
                    if (!reason.isEmpty()) {
                        ReportUserRequest reportUserDTO = new ReportUserRequest();
                        reportUserDTO.setReason(reason);

                        Long currentUserId = AuthService.getCurrentUser().getId();
                        reportUserDTO.setReportedByUserId(currentUserId);

                        ApiService.getUserService()
                                .reportUser(viewedUserId, reportUserDTO)
                                .enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(requireContext(), "User reported", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(requireContext(), "Failed to report user", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        Toast.makeText(requireContext(), "Reason cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
