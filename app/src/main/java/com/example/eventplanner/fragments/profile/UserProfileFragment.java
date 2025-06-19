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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;

public class UserProfileFragment extends Fragment {

        private ImageView profilePicture;
        private TextView userFullName, userEmail;
        private Button blockUserButton, reportUserButton;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_user_profile, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Povezivanje sa XML elementima
            profilePicture = view.findViewById(R.id.profile_picture);
            userFullName = view.findViewById(R.id.user_full_name);
            userEmail = view.findViewById(R.id.user_email);
            blockUserButton = view.findViewById(R.id.block_user_button);
            reportUserButton = view.findViewById(R.id.report_user_button);

            // Postavljanje demo podataka korisnika
            userFullName.setText("John Doe");
            userEmail.setText("john.doe@example.com");

            // Dugme za blokiranje
            blockUserButton.setOnClickListener(v -> showBlockConfirmationDialog());

            // Dugme za prijavu
            reportUserButton.setOnClickListener(v -> showReportDialog());
        }

        // Metod za prikaz dialoga za blokiranje
        private void showBlockConfirmationDialog() {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Block User")
                    .setMessage("Are you sure you want to block this user?")
                    .setPositiveButton("Block", (dialog, which) -> {
                        // Logika za blokiranje korisnika
                        Toast.makeText(requireContext(), "User blocked", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }

        // Metod za prikaz dialoga za prijavu
        private void showReportDialog() {
            // Prilagođeni layout za prijavu
            final EditText input = new EditText(requireContext());
            input.setHint("Enter reason for report");

            new AlertDialog.Builder(requireContext())
                    .setTitle("Report User")
                    .setMessage("Please enter a reason for reporting this user:")
                    .setView(input)
                    .setPositiveButton("Report", (dialog, which) -> {
                        String reason = input.getText().toString().trim();
                        if (!reason.isEmpty()) {
                            // Logika za prijavu korisnika
                            Toast.makeText(requireContext(), "User reported for: " + reason, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Reason cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }
