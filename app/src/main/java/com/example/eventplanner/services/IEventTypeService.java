package com.example.eventplanner.services;

import com.example.eventplanner.model.entities.Event;
import com.example.eventplanner.model.entities.EventType;
import com.example.eventplanner.model.eventTypeCreation.CategoriesResponse;
import com.example.eventplanner.model.eventTypeCreation.EventTypeCreationRequest;
import com.example.eventplanner.model.eventTypeCreation.EventTypeEditRequest;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IEventTypeService {

    @GET("users/admins/sp-categories")
    Call<Set<CategoriesResponse>> getActiveCategories();

    @GET("users/admins/event-type/get/all")
    Call<List<EventType>> getAllEventTypes();

    @POST("users/admins/event-type/create")
    Call<EventType> createEventType(@Body EventTypeCreationRequest eventTypeCreationRequest);

    @PUT("users/admins/event-type/change-activity/{id}")
    Call<EventType> changeEventTypeActivity(@Path("id") Long id);

    @PUT("users/admins/event-type/{id}/edit")
    Call<EventType> editEventType(@Path("id") Long id,@Body EventTypeEditRequest eventTypeEditRequest);
}
