package com.example.eventplanner.services;

import com.example.eventplanner.model.productDetails.ServiceDetailsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IServiceService {
    @GET("services/all")
    Call<List<ServiceDetailsResponse>> getAllServices();

}
