package com.example.eventplanner.services;

import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.model.homepage.ServiceProductHomeResponse;
import com.example.eventplanner.model.productDetails.FilterItemsOptions;
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
    @GET("services-products/sorted")
    Call<PagedResponse<ServiceProductHomeResponse>> getSortedServicesProducts(
            @Query("sortCriteria") List<String> sortCriteria,
            @Query("sortOrder") String sortOrder,
            @Query("page") int page,
            @Query("size") int size
    );
    @GET("services-products/filter-options")
    Call<FilterItemsOptions> getFilterOptions();
    @GET("services-products/filter")
    Call<PagedResponse<ServiceProductHomeResponse>> filterServicesProducts(
            @Query("type") String type,
            @Query("category") List<String> category,
            @Query("min") Double min,
            @Query("max") Double max,
            @Query("page") int page,
            @Query("size") int size
    );
    @GET("services-products/search")
    Call<PagedResponse<ServiceProductHomeResponse>> searchServicesProducts(
            @Query("query") String query,
            @Query("page") int page,
            @Query("size") int size
    );


}
