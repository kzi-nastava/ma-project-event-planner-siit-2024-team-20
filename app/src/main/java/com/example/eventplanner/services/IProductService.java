package com.example.eventplanner.services;

import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.model.homepage.ServiceProductHomeResponse;
import com.example.eventplanner.model.productDetails.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IProductService {
    @GET("services-products/top-5")
    Call<List<ServiceProductHomeResponse>> getTop5ServicesProducts();
    @GET("services-products")
    Call<PagedResponse<ServiceProductHomeResponse>> getPagedProducts(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );
    @GET("products/search")
    Call<List<ProductResponse>> searchProductsByName(@Query("name") String name);
    @GET("products/filter")
    Call<PagedResponse<ProductResponse>> filterProducts(
            @Query("category") String category,
            @Query("eventType") String eventType,
            @Query("minPrice") Double minPrice,
            @Query("maxPrice") Double maxPrice,
            @Query("available") Boolean available,
            @Query("description") String description,
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );



}
