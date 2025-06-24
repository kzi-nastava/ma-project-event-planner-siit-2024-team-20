package com.example.eventplanner.fragments.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.activities.startup.LoginActivity;
import com.example.eventplanner.model.entities.User;
import com.example.eventplanner.model.enumeration.Role;
import com.example.eventplanner.model.profile.ChangePasswordRequest;
import com.example.eventplanner.model.profile.ProfileResponse;
import com.example.eventplanner.model.profile.SppProfileResponse;
import com.example.eventplanner.model.profile.SppUpdateRequest;
import com.example.eventplanner.model.profile.UserUpdateRequest;
import com.example.eventplanner.services.IUserService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;
import com.example.eventplanner.services.spec.RetrofitClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.Reference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private GalleryFragment galleryFragment;
    private EditText editTextIme, editTextPrezime, editTextEmail, editTextGrad,
            editTextAdresa, editTextBrojAdrese, editTextTelefon,
            editTextCompanyName, editTextCompanyDescription;
    private LinearLayout companyFieldsContainer;
    private ProfileImageFragment profileImageFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageFragment = new ProfileImageFragment();
        galleryFragment = new GalleryFragment();

        //izmjena
        TextView buttonChange = view.findViewById(R.id.changeAccountButton);

        buttonChange.setOnClickListener(v -> {
            if (!areFieldsValid()) {
                Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = AuthService.getUserIdFromToken();
            if (userId == -1) {
                Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
                return;
            }

            String roleStr = AuthService.getRoleFromToken();
            Role role;
            try {
                role = Role.valueOf(roleStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                Toast.makeText(getContext(), "Unknown user role", Toast.LENGTH_SHORT).show();
                return;
            }

            IUserService userService = ApiService.getUserService();

            String imageBase64 = profileImageFragment.getImageBase64();

            if (role == Role.ROLE_SERVICE_PRODUCT_PROVIDER) {
                SppUpdateRequest request = new SppUpdateRequest(
                        (long) userId,
                        editTextIme.getText().toString(),
                        editTextPrezime.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextGrad.getText().toString(),
                        editTextAdresa.getText().toString(),
                        editTextBrojAdrese.getText().toString(),
                        editTextTelefon.getText().toString(),
                        imageBase64,
                        editTextCompanyName.getText().toString(),
                        editTextCompanyDescription.getText().toString(),
                        galleryFragment.getAllImages()
                );
                userService.editSppProfile((long) userId, request).enqueue(new Callback<SppProfileResponse>() {
                    @Override
                    public void onResponse(Call<SppProfileResponse> call, Response<SppProfileResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SppProfileResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                UserUpdateRequest request = new UserUpdateRequest(
                        (long) userId,
                        editTextIme.getText().toString(),
                        editTextPrezime.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextGrad.getText().toString(),
                        editTextAdresa.getText().toString(),
                        editTextBrojAdrese.getText().toString(),
                        editTextTelefon.getText().toString(),
                        imageBase64
                );

                if (role == Role.ROLE_EVENT_ORGANIZER) {
                    userService.editEoProfile((long) userId, request).enqueue(new Callback<ProfileResponse>() {
                        @Override
                        public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                requireActivity().finish();
                            } else {
                                Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProfileResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (role == Role.ROLE_ADMIN) {
                    userService.editAdminProfile((long) userId, request).enqueue(new Callback<ProfileResponse>() {
                        @Override
                        public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                requireActivity().finish();
                            } else {
                                Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProfileResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //deaktivacija
        TextView buttonDeactivate = view.findViewById(R.id.deactivateAccountButton);
        buttonDeactivate.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Deactivate Account")
                    .setMessage("Are you sure you want to deactivate your account?")
                    .setPositiveButton("Yes", (dialog, which) -> deactivateAndLogout())
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        //izmjena lozinke
        EditText editTextOldPassword = view.findViewById(R.id.editTextOldPassword);
        EditText editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmNewPassword = view.findViewById(R.id.editTextConfirmNewPassword);
        TextView buttonChangePassword = view.findViewById(R.id.changePasswordButton);

        buttonChangePassword.setOnClickListener(v -> {
            String oldPassword = editTextOldPassword.getText().toString().trim();
            String newPassword = editTextNewPassword.getText().toString().trim();
            String confirmNewPassword = editTextConfirmNewPassword.getText().toString().trim();

            if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                Toast.makeText(getContext(), "The new password can not be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                Toast.makeText(getContext(), "The new password and the confirmed password do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(oldPassword, newPassword);
        });


        getChildFragmentManager().beginTransaction()
                .replace(R.id.profileImageFragmentContainer, profileImageFragment)
                .commit();

        editTextIme = view.findViewById(R.id.editTextIme);
        editTextPrezime = view.findViewById(R.id.editTextPrezime);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextGrad = view.findViewById(R.id.editTextGrad);
        editTextAdresa = view.findViewById(R.id.editTextAdresa);
        editTextBrojAdrese = view.findViewById(R.id.editTextBrojAdrese);
        editTextTelefon = view.findViewById(R.id.editTextTelefon);
        editTextCompanyName = view.findViewById(R.id.editTextCompanyName);
        editTextCompanyDescription = view.findViewById(R.id.editTextCompanyDescription);
        companyFieldsContainer = view.findViewById(R.id.companyFieldsContainer);

        loadUserData();

        Switch switchChangePassword = view.findViewById(R.id.switchChangePassword);
        LinearLayout passwordFieldsContainer = view.findViewById(R.id.passwordFieldsContainer);

        switchChangePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                passwordFieldsContainer.setVisibility(View.VISIBLE);
            } else {
                passwordFieldsContainer.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void loadUserData() {
        int userId = AuthService.getUserIdFromToken();
        if (userId == -1) {
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        String userRoleStr = AuthService.getRoleFromToken();
        Role userRole;

        try {
            userRole = Role.valueOf(userRoleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Unknown role", Toast.LENGTH_SHORT).show();
            return;
        }
        IUserService userService = ApiService.getUserService();

        //Call<?> call = null;

        /*if (userRole == Role.ROLE_ADMIN) {
            call = userService.getAdminById((long) userId);
        } else if (userRole == Role.ROLE_EVENT_ORGANIZER) {
            call = userService.getEOById((long) userId);
        } else if (userRole == Role.ROLE_SERVICE_PRODUCT_PROVIDER) {
            call = userService.getSPPById((long) userId);
        } else {
            Toast.makeText(getContext(), "Unknown role", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (userRole == Role.ROLE_ADMIN) {
            Call<ProfileResponse> call = userService.getAdminById((long) userId);
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        updateUI(response.body(), userRole);
                    } else {
                        Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (userRole == Role.ROLE_EVENT_ORGANIZER) {
            Call<ProfileResponse> call = userService.getEOById((long) userId);
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        updateUI(response.body(), userRole);
                    } else {
                        Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (userRole == Role.ROLE_SERVICE_PRODUCT_PROVIDER) {
            Call<SppProfileResponse> call = userService.getSPPById((long) userId);
            call.enqueue(new Callback<SppProfileResponse>() {
                @Override
                public void onResponse(Call<SppProfileResponse> call, Response<SppProfileResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        updateUI(response.body());
                    } else {
                        Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SppProfileResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Unknown role", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI(ProfileResponse profile, Role userRole) {
        editTextIme.setText(profile.getName());
        editTextPrezime.setText(profile.getLastName());
        editTextEmail.setText(profile.getEmail());
        editTextGrad.setText(profile.getCity());
        editTextAdresa.setText(profile.getAddress());
        editTextBrojAdrese.setText(String.valueOf(profile.getAddressNum()));
        editTextTelefon.setText(profile.getPhoneNumber());

        if (profileImageFragment != null) {
            Bitmap correctedBitmap = correctImageOrientation(profile.getProfileImage());
            if (correctedBitmap != null) {
                String correctedBase64Image = encodeBitmapToBase64(correctedBitmap);
                profileImageFragment.setImageBase64(correctedBase64Image);
            } else {
                profileImageFragment.setImageBase64(profile.getProfileImage());
            }
        }

        companyFieldsContainer.setVisibility(userRole == Role.ROLE_SERVICE_PRODUCT_PROVIDER ? View.VISIBLE : View.GONE);
    }

    private void updateUI(SppProfileResponse profile) {
        editTextIme.setText(profile.getName());
        editTextPrezime.setText(profile.getLastName());
        editTextEmail.setText(profile.getEmail());
        editTextGrad.setText(profile.getCity());
        editTextAdresa.setText(profile.getAddress());
        editTextBrojAdrese.setText(String.valueOf(profile.getAddressNum()));
        editTextTelefon.setText(profile.getPhoneNumber());

        if (profileImageFragment != null) {
            Bitmap correctedBitmap = correctImageOrientation(profile.getProfileImage());
            if (correctedBitmap != null) {
                String correctedBase64Image = encodeBitmapToBase64(correctedBitmap);
                profileImageFragment.setImageBase64(correctedBase64Image);
            } else {
                profileImageFragment.setImageBase64(profile.getProfileImage());
            }
        }

        editTextCompanyName.setText(profile.getCompanyName());
        editTextCompanyDescription.setText(profile.getCompanyDescription());
        companyFieldsContainer.setVisibility(View.VISIBLE);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.companyFieldsContainer, galleryFragment)
                .runOnCommit(() -> {
                    galleryFragment.setBase64Images(profile.getImages());
                })
                .commit();

    }
    private Bitmap correctImageOrientation(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            return null;
        }
        try {
            byte[] decodedBytes = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);

            ExifInterface exif = new ExifInterface(inputStream);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                return rotateImage(bitmap, 90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                return rotateImage(bitmap, 180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                return rotateImage(bitmap, 270);
            } else {
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap rotateImage(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
    }
    private void changePassword(String oldPassword, String newPassword) {
        IUserService userService = ApiService.getUserService();
        int userId = AuthService.getUserIdFromToken();

        if (userId == -1) {
            Toast.makeText(getContext(), "User is not autentificated", Toast.LENGTH_SHORT).show();
            return;
        }

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setOldPassword(oldPassword);
        changePasswordRequest.setNewPassword(newPassword);

        Call<ProfileResponse> call = userService.changePassword((long)userId, changePasswordRequest);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error when changing password. Please, try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deactivateAndLogout() {
        int userId = AuthService.getUserIdFromToken();
        if (userId == -1) {
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        IUserService userService = ApiService.getUserService();
        Call<ProfileResponse> call = userService.deactivateAccount((long) userId);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Account deactivated", Toast.LENGTH_SHORT).show();
                    AuthService.logout();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to deactivate account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
        private boolean areFieldsValid() {
            if (editTextIme.getText().toString().trim().isEmpty() ||
                    editTextPrezime.getText().toString().trim().isEmpty() ||
                    editTextEmail.getText().toString().trim().isEmpty() ||
                    editTextGrad.getText().toString().trim().isEmpty() ||
                    editTextAdresa.getText().toString().trim().isEmpty() ||
                    editTextBrojAdrese.getText().toString().trim().isEmpty() ||
                    editTextTelefon.getText().toString().trim().isEmpty()) {
                return false;
            }

            if (companyFieldsContainer.getVisibility() == View.VISIBLE) {
                return !editTextCompanyName.getText().toString().trim().isEmpty() &&
                        !editTextCompanyDescription.getText().toString().trim().isEmpty();
            }

            return true;
        }


    }