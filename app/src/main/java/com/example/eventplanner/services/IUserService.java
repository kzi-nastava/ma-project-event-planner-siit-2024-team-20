package com.example.eventplanner.services;

import com.example.eventplanner.model.entities.User;
import com.example.eventplanner.model.login.LoginRequest;
import com.example.eventplanner.model.login.LoginResponse;
import com.example.eventplanner.model.registration.EoRegistrationRequest;
import com.example.eventplanner.model.registration.EoRegistrationResponse;
import com.example.eventplanner.model.registration.SppRegistrationRequest;
import com.example.eventplanner.model.registration.SppRegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
}
