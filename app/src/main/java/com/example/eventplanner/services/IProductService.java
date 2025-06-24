package com.example.eventplanner.services;

import com.example.eventplanner.model.homepage.PagedResponse;
import com.example.eventplanner.model.homepage.ServiceProductHomeResponse;
import com.example.eventplanner.model.productDetails.FilterItemsOptions;
import com.example.eventplanner.model.productDetails.ProductDetailsResponse;
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
import retrofit2.http.Query;

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

    @DELETE("products/{id}/deactivate")
    Call<Boolean> deactivateProduct(@Path("id") Long id);

    @GET("products/filter/{id}/sp-products")
    Call<List<ProvidersProductsResponse>> getFilteredProvidersProduct(
            @Path("id") Long id,
            @Query("category") String category,
            @Query("type") String type,
            @Query("price") String price,
            @Query("available") String available
    );
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
    @GET("products/{id}")
    Call<ProductDetailsResponse> getProductDetails(@Path("id") Long id);
}
