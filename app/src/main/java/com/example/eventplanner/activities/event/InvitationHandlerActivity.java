package com.example.eventplanner.activities.event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.activities.startup.LoginActivity;
import com.example.eventplanner.activities.startup.QuickRegistrationActivity;
import com.example.eventplanner.helpers.ErrorResponse;
import com.example.eventplanner.services.spec.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationHandlerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        if (data != null) {
            String email = data.getQueryParameter("email");
            String eventId = data.getQueryParameter("eventId");

            Call<ErrorResponse> call = ApiService.getEventService()
                    .confirmEventAttendance(email, Long.parseLong(eventId));

            call.enqueue(new Callback<ErrorResponse>() {
                @Override
                public void onResponse(Call<ErrorResponse> call, Response<ErrorResponse> response) {
                    Log.d("INVITATION_HANDLER", "Response code: " + response.code());
                    if (response.isSuccessful() && response.body() != null) {
                        String message = response.body().getMessage();
                        Log.d("INVITATION_HANDLER", "Message: " + message);

                        switch (message) {
                            case "Event added to your accepted list!":
                                Toast.makeText(InvitationHandlerActivity.this, "Successfully confirmed!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(InvitationHandlerActivity.this, HomeActivity.class));
                                finish();
                                break;

                            case "Please log in to confirm attendance.":
                                Toast.makeText(InvitationHandlerActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent loginIntent = new Intent(InvitationHandlerActivity.this, LoginActivity.class);
                                loginIntent.putExtra("email", email);
                                loginIntent.putExtra("eventId", eventId);
                                startActivity(loginIntent);
                                finish();
                                break;

                            case "User not found. Please register.":
                                Toast.makeText(InvitationHandlerActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent regIntent = new Intent(InvitationHandlerActivity.this, QuickRegistrationActivity.class);
                                regIntent.putExtra("email", email);
                                regIntent.putExtra("eventId", eventId);
                                startActivity(regIntent);
                                finish();
                                break;

                            default:
                                Toast.makeText(InvitationHandlerActivity.this, message, Toast.LENGTH_LONG).show();
                                finish();
                                break;
                        }

                    } else {
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "empty";
                            Log.e("INVITATION_HANDLER", "Error response code: " + response.code() + ", body: " + errorBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(InvitationHandlerActivity.this, "Something went wrong! Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<ErrorResponse> call, Throwable t) {
                    Log.e("INVITATION_HANDLER", "Network error: " + t.getMessage(), t);
                    Toast.makeText(InvitationHandlerActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }
            });


        } else {
            Toast.makeText(this, "Invalid link: missing email or eventId", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
