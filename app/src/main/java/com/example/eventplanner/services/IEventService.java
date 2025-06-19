package com.example.eventplanner.services;

import com.example.eventplanner.model.eventCreation.EventCreationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IEventService {

    @GET("events/event-type/{id}/sp-categories/suggestions")
    Call<List<String>> getSuggestedCategoriesForType(@Path("id") Long id);
    @POST("events/creation")
    Call<Void> createEvent(@Body EventCreationRequest eventCreationRequest);

}
