package com.example.eventplanner.services;

import com.example.eventplanner.model.notification.NotificationResponse;
import com.example.eventplanner.model.notification.NotificationSettingsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface INotificationService {
    @GET("notifications/{userId}")
    Call<List<NotificationResponse>> getUserNotifications(@Path("userId") Long userId);

    @PUT("notifications/{id}/mark-as-read")
    Call<NotificationResponse> markNotificationAsRead(@Path("id") Long id);

    @PUT("notifications/{userId}/mute")
    Call<NotificationSettingsResponse> muteNotifications(@Path("userId") Long userId, @Body NotificationSettingsResponse muteStatus);

    @GET("notifications/{userId}/mute")
    Call<NotificationSettingsResponse> getMuteStatus(@Path("userId") Long userId);
}
