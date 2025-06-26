package com.example.eventplanner.activities.startup;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;

public class QuickRegistrationActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quick_registration);
        Uri data = getIntent().getData();
        if (data != null) {
            String email = data.getQueryParameter("email");
            String eventId = data.getQueryParameter("eventId");

            // npr. popuni email polje automatski
            EditText emailField = findViewById(R.id.editTextTextEmailAddress);
            emailField.setText(email);

            // možeš čuvati eventId ako ti treba dalje
            Log.d("QUICK_REG", "Email: " + email + ", Event ID: " + eventId);
        }
        editTextName = findViewById(R.id.editTextText);
        editTextLastName = findViewById(R.id.editTextText2);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextEmail.setEnabled(false);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextTextPassword2);
        btnRegister = findViewById(R.id.btnregister);

        btnRegister.setOnClickListener(v -> {
            if (validateInput()) {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                // Ovde API poziv, čuvanje u bazi itd.
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

        // Ako sve prođe, vraćamo true
        return true;
    }
}