package com.example.eventplanner.services;

import com.example.eventplanner.model.productDetails.ServiceDetailsResponse;

import java.util.List;

import retrofit2.http.GET;

public class IServiceService {
    @GET("services/all")
    Call<List<ServiceDetailsResponse>> getAllServices();

}
