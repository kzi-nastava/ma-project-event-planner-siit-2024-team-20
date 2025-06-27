package com.example.eventplanner.services;

import com.example.eventplanner.model.review.CommentResponse;
import com.example.eventplanner.model.review.CommentViewResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ICommentService {
    @GET("api/comments/pending")
    Call<List<CommentResponse>> getPendingComments();

    @GET("comments/{id}")
    Call<CommentResponse> getCommentById(@Path("id") Long id);

    @PUT("api/comments/{id}/approve")
    Call<CommentResponse> approveComment(@Path("id") Long id);

    @DELETE("api/comments/{id}")
    Call<CommentResponse> deleteComment(@Path("id") Long id);

    @GET("api/comments/item/{id}")
    Call<List<CommentViewResponse>> getItemComments(@Path("id") Long itemId);
}
