package com.example.eventplanner.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.users.ReportViewResponse;
import com.example.eventplanner.model.users.SuspensionResponse;
import com.example.eventplanner.services.spec.ApiService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<ReportViewResponse> reports;
    private Runnable refreshCallback;

    public ReportAdapter(List<ReportViewResponse> reports, Runnable refreshCallback) {
        this.reports = reports;
        this.refreshCallback = refreshCallback;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportViewResponse report = reports.get(position);

        holder.tvReporter.setText(report.getReportedByUserEmail());
        holder.tvReportedUser.setText(report.getReportedUserEmail() );
        holder.tvReason.setText(report.getReason());
        LocalDateTime dateTime = report.getCreatedAt(); // već je LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = dateTime.format(formatter);
        holder.tvDate.setText(formattedDate);
        holder.ivSuspend.setOnClickListener(v -> {
            ApiService.getReportService().suspendUser(report.getId())
                    .enqueue(new Callback<SuspensionResponse>() {
                        @Override
                        public void onResponse(Call<SuspensionResponse> call, Response<SuspensionResponse> response) {
                            try {
                                if (response.isSuccessful() && response.body() != null) {
                                    Toast.makeText(v.getContext(), "User suspended", Toast.LENGTH_SHORT).show();
                                    refreshCallback.run();
                                } else if (response.code() == 401) {
                                    Toast.makeText(v.getContext(), "User is already suspended", Toast.LENGTH_SHORT).show();
                                    refreshCallback.run();
                                } else {
                                    String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                                    Log.e("ReportAdapter", "Suspend failed: " + errorBody);
                                    Toast.makeText(v.getContext(), "Failed to suspend: " + errorBody, Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(v.getContext(), "Failed to suspend (exception)", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SuspensionResponse> call, Throwable t) {
                            Log.e("SuspendUser", "onFailure called: " + t.toString(), t);
                            Toast.makeText(v.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            });

        holder.ivDelete.setOnClickListener(v -> {
            ApiService.getReportService().deleteReport(report.getId())
                    .enqueue(new Callback<ReportViewResponse>() {
                        @Override
                        public void onResponse(Call<ReportViewResponse> call, Response<ReportViewResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(v.getContext(), "Report deleted", Toast.LENGTH_SHORT).show();
                                refreshCallback.run();
                            } else {
                                Toast.makeText(v.getContext(), "Failed to delete report", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReportViewResponse> call, Throwable t) {
                            Toast.makeText(v.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvReporter, tvReportedUser, tvReason, tvDate;
        ImageView ivSuspend, ivDelete;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReporter = itemView.findViewById(R.id.tv_reporter);
            tvReportedUser = itemView.findViewById(R.id.tv_reported_user);
            tvReason = itemView.findViewById(R.id.tv_reason);
            tvDate = itemView.findViewById(R.id.tv_date);
            ivSuspend = itemView.findViewById(R.id.iv_suspend);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
