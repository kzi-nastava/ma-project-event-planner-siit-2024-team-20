package com.example.eventplanner.services;

import com.example.eventplanner.model.entities.User;
import com.example.eventplanner.model.eventPage.EventDisplayResponse;
import com.example.eventplanner.model.login.LoginRequest;
import com.example.eventplanner.model.login.LoginResponse;
import com.example.eventplanner.model.profile.ChangePasswordRequest;
import com.example.eventplanner.model.profile.ProfileResponse;
import com.example.eventplanner.model.profile.SppProfileResponse;
import com.example.eventplanner.model.profile.SppUpdateRequest;
import com.example.eventplanner.model.profile.UserUpdateRequest;
import com.example.eventplanner.model.registration.EoRegistrationRequest;
import com.example.eventplanner.model.registration.EoRegistrationResponse;
import com.example.eventplanner.model.registration.SppRegistrationRequest;
import com.example.eventplanner.model.registration.SppRegistrationResponse;
import com.example.eventplanner.model.users.QuickRegistrationRequest;
import com.example.eventplanner.model.users.QuickUserResponse;
import com.example.eventplanner.model.users.ReportUserRequest;
import com.example.eventplanner.model.users.UserResponse;
import com.example.eventplanner.model.users.UserViewResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("users/login")
    Call<LoginResponse> userLogin(@Body LoginRequest request);

    @Headers("Content-Type: application/json")
    @GET("users/currentUser")
    Call<User> getCurrentUser();

    @Headers("Content-Type: application/json")
    @POST("users/event-organizers/register")
    Call<EoRegistrationResponse> eoRegistration(@Body EoRegistrationRequest request);

    @Headers("Content-Type: application/json")
    @POST("users/sp-providers/register")
    Call<SppRegistrationResponse> sppRegistration(@Body SppRegistrationRequest request);

    @Headers("Content-Type: application/json")
    @GET("users/admins/{id}")
    Call<ProfileResponse> getAdminById(@retrofit2.http.Path("id") Long id);

    @Headers("Content-Type: application/json")
    @GET("users/event-organizers/{id}")
    Call<ProfileResponse> getEOById(@retrofit2.http.Path("id") Long id);

    @Headers("Content-Type: application/json")
    @GET("users/sp-providers/{id}")
    Call<SppProfileResponse> getSPPById(@retrofit2.http.Path("id") Long id);

    @PUT("users/{id}/changePassword")
    Call<ProfileResponse> changePassword(@Path("id") Long id, @Body ChangePasswordRequest changePasswordRequest);

    @DELETE("users/{id}/deactivate")
    Call<ProfileResponse> deactivateAccount(@Path("id") Long id);

    @PUT("users/event-organizers/{id}/edit")
    Call<ProfileResponse> editEoProfile(@Path("id") Long id, @Body UserUpdateRequest userUpdateRequest);

    @PUT("users/admins/{id}/edit")
    Call<ProfileResponse> editAdminProfile(@Path("id") Long id, @Body UserUpdateRequest userUpdateRequest);

    @PUT("users/sp-providers/{id}/edit")
    Call<SppProfileResponse> editSppProfile(@Path("id") Long id, @Body SppUpdateRequest sppUpdateRequest);

    @GET("users/{userId}/calendar")
    Call<List<EventDisplayResponse>> getCalendar(@Path("userId") int userId);

    @POST("users/quick-registration")
    Call<QuickUserResponse> quickRegister(@Body QuickRegistrationRequest request);

    @POST("users/block/{blockedId}")
    Call<UserViewResponse> blockUser(@Path("blockedId") Long blockedId);

    @DELETE("users/{id}/blocked/{blockedId}")
    Call<Void> unblockUser(@Path("id") Long userId, @Path("blockedId") Long blockedId);

    @GET("users/{id}/blocked")
    Call<List<UserResponse>> getBlockedUsers(@Path("id") Long userId);

    @GET("users/{id}")
    Call<UserViewResponse> getUserById(@Path("id") Long id);

    @POST("users/{id}/report")
    Call<Void> reportUser(@Path("id") Long id,@Body ReportUserRequest reportData);

    @PUT("users/logout-fcm")
    Call<Void> logoutFcmToken();
}
