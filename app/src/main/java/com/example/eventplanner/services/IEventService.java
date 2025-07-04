package com.example.eventplanner.services;

import com.example.eventplanner.helpers.ErrorResponse;
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
    @GET("events")
    Call<PagedResponse<EventHomeResponse>> getAllEventsPaged(
            @Query("query") String query,
            @Query("type") List<String> type,
            @Query("city") List<String> city,
            @Query("dateAfter") String dateAfter,
            @Query("dateBefore") String dateBefore,
            @Query("sortCriteria") List<String> sortCriteria,
            @Query("sortOrder") String sortOrder,
            @Query("page") int page,
            @Query("size") int size
    );
    @GET("events/filter-options")
    Call<FilterEventResponse> getFilterOptions();
    @GET("events/{id}")
    Call<EventDisplayResponse> getEvent(@Path("id") Long id);

    @PUT("users/{userId}/event/add-favourite")
    Call<Boolean> addToFavourites(@Path("userId") Long userId, @Query("eventId") Long eventId);

    @GET("users/{userId}/event/is-favourite")
    Call<Boolean> isInFavourites(@Path("userId") Long userId, @Query("eventId") Long eventId);

    @GET("events/{id}/additionally")
    Call<EventStatResponse> getStat(@Path("id") Long id);

    @GET("events/get-mine")
    Call<List<EventHomeResponse>> getMyEvents();
    @GET("events/event-confirmation")
    Call<ErrorResponse> confirmEventAttendance(
            @Query("email") String email,
            @Query("eventId") Long eventId
    );

}
