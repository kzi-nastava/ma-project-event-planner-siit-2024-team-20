package com.example.eventplanner.fragments.startup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.startup.LoginActivity;
import com.example.eventplanner.model.registration.EoRegistrationRequest;
import com.example.eventplanner.model.registration.EoRegistrationResponse;
import com.example.eventplanner.model.registration.SppRegistrationRequest;
import com.example.eventplanner.model.registration.SppRegistrationResponse;
import com.example.eventplanner.services.IUserService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SppRegistrationFragment extends Fragment {

    private EditText nameField, lastNameField, emailField, passwordField, confirmPasswordField, cityField, addressField, addressNumField, phoneNumberField, companyNameField, companyDescriptionField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spp_registration, container, false);
        nameField = view.findViewById(R.id.editTextText);
        lastNameField = view.findViewById(R.id.editTextText2);
        emailField = view.findViewById(R.id.editTextTextEmailAddress);
        passwordField = view.findViewById(R.id.editTextTextPassword);
        confirmPasswordField = view.findViewById(R.id.editTextTextPassword2);
        cityField = view.findViewById(R.id.editTextText3);
        addressField = view.findViewById(R.id.editTextText5);
        addressNumField = view.findViewById(R.id.editTextText4);
        phoneNumberField = view.findViewById(R.id.editTextPhone);
        companyNameField = view.findViewById(R.id.editTextText6);
        companyDescriptionField = view.findViewById(R.id.editTextText7);

        Button registerButton = view.findViewById(R.id.btnregister);
        registerButton.setOnClickListener(v -> registerSPProvider());
        return view;
    }

    private void registerSPProvider() {
        String name = nameField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        String city = cityField.getText().toString().trim();
        String address = addressField.getText().toString().trim();
        String addressNum = addressNumField.getText().toString().trim();
        String phoneNumber = phoneNumberField.getText().toString().trim();
        String companyName = companyNameField.getText().toString().trim();
        String companyDescription = companyDescriptionField.getText().toString().trim();

        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || city.isEmpty() || address.isEmpty() || addressNum.isEmpty() ||
                phoneNumber.isEmpty() || companyName.isEmpty() || companyDescription.isEmpty()) {
            Toast.makeText(getContext(), "All fields must be filled.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isGmailAddress(email)){
            Toast.makeText(getContext(), "Email must be valid gmail address.", Toast.LENGTH_SHORT).show();
            return;
        }
        SppRegistrationRequest request = new SppRegistrationRequest(name, lastName, email, password, city, address, addressNum, phoneNumber, companyName, companyDescription);

        IUserService userService = RetrofitClient.getClient(ApiService.BASE_URL)
                .create(IUserService.class);
        Call<SppRegistrationResponse> call = userService.sppRegistration(request);
        call.enqueue(new Callback<SppRegistrationResponse>() {
            @Override
            public void onResponse(Call<SppRegistrationResponse> call, Response<SppRegistrationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Registration successful! Check your gmail to verify profile", Toast.LENGTH_SHORT).show();
                    new android.os.Handler().postDelayed(() -> {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }, 3000);
                } else {
                    Toast.makeText(getContext(), "Registration error. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SppRegistrationResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Connection error, try again later.", Toast.LENGTH_SHORT).show();
            }
            
        });
    }
    private boolean isGmailAddress(String email) {
        return email != null && email.endsWith("@gmail.com");
    }

}