package com.example.eventplanner.services;

import com.example.eventplanner.model.productDetails.ServiceDetailsResponse;
import com.example.eventplanner.model.serviceReservation.ServiceBookingRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IServiceService {
    @GET("services/all")
    Call<List<ServiceDetailsResponse>> getAllServices();

    @GET("services/{id}")
    Call<ServiceDetailsResponse> getServiceDetails(@Path("id") Long id);

    @POST("services/{id}/booking")
    Call<ServiceBookingRequest> bookService(@Path("id") Long id, @Body ServiceBookingRequest bookingDTO);

}
