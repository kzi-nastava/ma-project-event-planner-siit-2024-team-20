package com.example.eventplanner.services;

import com.example.eventplanner.model.productManage.CategoryResponse;
import com.example.eventplanner.model.productManage.ProductCreationRequest;
import com.example.eventplanner.model.productManage.ProductResponse;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IServiceProductService {

    @GET("users/admins/sp-categories")
    Call<Set<CategoryResponse>> getActiveSPCategories();

    @GET("events/event-types/all/active")
    Call<List<String>> getAllActiveEventTypesNames();

    @POST("products/add/{id}")
    Call<ProductResponse> addProduct(@Path("id") Long id, @Body ProductCreationRequest productCreationRequest);
}
