package com.example.eventplanner.services;

import com.example.eventplanner.model.eventCreation.EventCreationRequest;
import com.example.eventplanner.model.eventDetails.EventDetailsResponse;
import com.example.eventplanner.model.eventDetails.EventStatResponse;
import com.example.eventplanner.model.eventDetails.FilterEventResponse;
import com.example.eventplanner.model.eventPage.EventDisplayResponse;
import com.example.eventplanner.model.homepage.EventHomeResponse;
import com.example.eventplanner.model.homepage.PagedResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IEventService {

    @GET("events/event-type/{id}/sp-categories/suggestions")
    Call<List<String>> getSuggestedCategoriesForType(@Path("id") Long id);
    @POST("events/creation")
    Call<Void> createEvent(@Body EventCreationRequest eventCreationRequest);
    @GET("events/top-5")
    Call<List<EventHomeResponse>> getTop5Events();
    @GET("events/all")
    Call<List<EventDetailsResponse>> getAllEvents();
    @GET("events")
    Call<PagedResponse<EventHomeResponse>> getAllEventsPaged(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );
    @GET("events/search")
    Call<PagedResponse<EventHomeResponse>> searchEvents(
            @Query("query") String query,
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );
    @GET("events/filter-options")
    Call<FilterEventResponse> getFilterOptions();
    @GET("events/filter")
    Call<PagedResponse<EventHomeResponse>> filterEvents(
            @Query("type") List<String> types,
            @Query("city") List<String> cities,
            @Query("dateAfter") String dateAfter,     // format: yyyy-MM-dd
            @Query("dateBefore") String dateBefore,   // format: yyyy-MM-dd
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );
    @GET("events/sorted")
    Call<PagedResponse<EventHomeResponse>> getSortedEvents(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sortCriteria") List<String> sortCriteria,
            @Query("sortOrder") String sortOrder
    );
    @GET("events/{id}")
    Call<EventDisplayResponse> getEvent(@Path("id") Long id);

    @PUT("users/{userId}/event/add-favourite")
    Call<Boolean> addToFavourites(@Path("userId") Long userId, @Query("eventId") Long eventId);

    @GET("users/{userId}/event/is-favourite")
    Call<Boolean> isInFavourites(@Path("userId") Long userId, @Query("eventId") Long eventId);

    @GET("events/{id}/additionally")
    Call<EventStatResponse> getStat(@Path("id") Long id);

}
