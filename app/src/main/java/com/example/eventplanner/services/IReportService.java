package com.example.eventplanner.services;

import com.example.eventplanner.model.users.ReportViewResponse;
import com.example.eventplanner.model.users.SuspensionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IReportService {
    @GET("reports")
    Call<List<ReportViewResponse>> getPendingReports();
    @POST("reports/suspend/{id}")
    Call<SuspensionResponse> suspendUser(@Path("id") Long id);

    @DELETE("reports/{id}")
    Call<ReportViewResponse> deleteReport(@Path("id") Long id);
}
