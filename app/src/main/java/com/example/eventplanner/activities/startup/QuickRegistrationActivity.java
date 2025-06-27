package com.example.eventplanner.activities.startup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.model.login.LoginRequest;
import com.example.eventplanner.model.login.LoginResponse;
import com.example.eventplanner.model.users.QuickRegistrationRequest;
import com.example.eventplanner.model.users.QuickUserResponse;
import com.example.eventplanner.services.IUserService;
import com.example.eventplanner.services.spec.ApiService;
import com.example.eventplanner.services.spec.AuthService;
import com.example.eventplanner.services.spec.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickRegistrationActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private RadioGroup roleRadioGroup;
    private String email;
    private String eventId;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quick_registration);
        Uri data = getIntent().getData();
        if (data != null) {
            email = data.getQueryParameter("email");
            eventId = data.getQueryParameter("eventId");
        } else {
            // Ako nema Uri, pokušaj iz Intent extras
            email = getIntent().getStringExtra("email");
            eventId = getIntent().getStringExtra("eventId");
        }
            // možeš čuvati eventId ako ti treba dalje
            Log.d("QUICK_REG", "Email: " + email + ", Event ID: " + eventId);

        editTextName = findViewById(R.id.editTextText);
        editTextLastName = findViewById(R.id.editTextText2);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextEmail.setEnabled(false);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextTextPassword2);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        btnRegister = findViewById(R.id.btnregister);
        editTextEmail.setText(email);
        btnRegister.setOnClickListener(v -> {
            if (validateInput()) {
                performQuickRegistration();
            }
        });
    }
    private boolean validateInput() {
        // Uzimanje unetih vrednosti
        String name = editTextName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validacija imena
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Name is required!");
            return false;
        }

        // Validacija prezimena
        if (TextUtils.isEmpty(lastName)) {
            editTextLastName.setError("Last name is required!");
            return false;
        }

        // Validacija email-a
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid email format!");
            return false;
        }

        // Validacija lozinke
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required!");
            return false;
        } else if (password.length() < 8) {
            editTextPassword.setError("Password must be at least 8 characters long!");
            return false;
        }

        // Validacija potvrde lozinke
        if (TextUtils.isEmpty(confirmPassword)) {
            editTextConfirmPassword.setError("Confirm Password is required!");
            return false;
        } else if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Passwords do not match!");
            return false;
        }
        int selectedId = roleRadioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        }

        RadioButton selectedRoleButton = findViewById(selectedId);
        String selectedRole = selectedRoleButton.getText().toString();

        // Ako sve prođe, vraćamo true
        return true;
    }
    private void performQuickRegistration() {
        Log.d("QUICK_REG", "Starting quick registration...");
        String firstName = editTextName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Long eventIdLong = Long.parseLong(eventId);

        int selectedId = roleRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedRole = selectedRadioButton.getText().toString();
        Log.d("QUICK_REG", "DTO: " + firstName + " " + lastName + " " + email + " " + selectedRole + " " + eventIdLong);
        QuickRegistrationRequest dto = new QuickRegistrationRequest(
                firstName,
                lastName,
                email,
                password,
                selectedRole,
                eventIdLong
        );
        Log.d("QUICK_REG", "DTO: " + firstName + " " + lastName + " " + email + " " + selectedRole + " " + eventIdLong);

        ApiService.getUserService().quickRegister(dto).enqueue(new Callback<QuickUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuickUserResponse> call, @NonNull Response<QuickUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Toast.makeText(QuickRegistrationActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    autoLoginAfterRegistration(email, password);

                    Intent intent = new Intent(QuickRegistrationActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(QuickRegistrationActivity.this, "Email is already in use!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuickUserResponse> call, @NonNull Throwable t) {
                Toast.makeText(QuickRegistrationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void autoLoginAfterRegistration(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        IUserService userService = RetrofitClient.getClient(ApiService.BASE_URL)
                .create(IUserService.class);

        Call<LoginResponse> call = userService.userLogin(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    AuthService.setToken(loginResponse.getToken());
                    new AuthService().getMyInfo();

                    Intent intent = new Intent(QuickRegistrationActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(QuickRegistrationActivity.this, "Auto-login failed after registration.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(QuickRegistrationActivity.this, "Error during auto-login: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AUTO_LOGIN_FAILURE", "Error: " + t.getMessage(), t);
            }
        });
    }

}