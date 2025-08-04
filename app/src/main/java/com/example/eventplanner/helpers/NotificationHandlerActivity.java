package com.example.eventplanner.helpers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.model.notification.NotificationResponse;
import com.example.eventplanner.services.spec.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long notificationId = getIntent().getLongExtra("notificationId", -1);

        if (notificationId != -1) {
            if (notificationId != -1) {
                NotificationManagerCompat.from(this).cancel((int)notificationId);
            }
            ApiService.getNotificationService()
                    .markNotificationAsRead(notificationId)
                    .enqueue(new Callback<NotificationResponse>() {
                        @Override
                        public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                            Log.d("NOTIF", "Marked as read");
                        }

                        @Override
                        public void onFailure(Call<NotificationResponse> call, Throwable t) {
                            Log.e("NOTIF", "Failed: " + t.getMessage());
                        }
                    });

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel((int) notificationId);
        }

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

