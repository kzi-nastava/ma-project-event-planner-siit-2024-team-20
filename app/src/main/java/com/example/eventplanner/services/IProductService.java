package com.example.eventplanner.services;

import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.model.homepage.ProductHomeResponse;
import com.example.eventplanner.model.productDetails.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IProductService {
    @GET("products/all")
    Call<List<ProductResponse>> getAllProducts();
    @GET("products")
    Call<PagedResponse<ProductHomeResponse>> getPagedProducts(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );
    @GET("products/search")
    Call<List<ProductResponse>> searchProductsByName(@Query("name") String name);



}
