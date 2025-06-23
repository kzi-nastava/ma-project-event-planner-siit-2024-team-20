package com.example.eventplanner.services;

import com.example.eventplanner.model.productManage.CategoryResponse;
import com.example.eventplanner.model.productManage.ProductCreationRequest;
import com.example.eventplanner.model.productManage.ProductEditRequest;
import com.example.eventplanner.model.productManage.ProductResponse;
import com.example.eventplanner.model.productManage.ProvidersProductsResponse;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IProductService {

    @GET("users/admins/sp-categories")
    Call<Set<CategoryResponse>> getActiveSPCategories();

    @GET("events/event-types/all/active")
    Call<List<String>> getAllActiveEventTypesNames();

    @POST("products/add/{id}")
    Call<ProductResponse> addProduct(@Path("id") Long id, @Body ProductCreationRequest productCreationRequest);


    @GET("products/{userId}/sp-products")
    Call<List<ProvidersProductsResponse>> getProvidersProducts(@Path("userId") Long userId);

    @PUT("products/{id}/edit")
    Call<Boolean> editProduct(@Path("id") Long id, @Body ProductEditRequest productEditRequest);

    @DELETE("{id}/deactivate")
    Call<Boolean> deactivateProduct(@Path("id") Long id);
}
