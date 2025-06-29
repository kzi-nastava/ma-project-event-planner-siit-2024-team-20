package com.example.eventplanner.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.adapters.ReportAdapter;
import com.example.eventplanner.model.users.ReportViewResponse;
import com.example.eventplanner.services.spec.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportManagementFragment extends Fragment {

    private RecyclerView rvReports;
    private ReportAdapter adapter;
    private List<ReportViewResponse> reportList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvReports = view.findViewById(R.id.rv_reports);
        rvReports.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ReportAdapter(reportList, this::refreshReports);
        rvReports.setAdapter(adapter);

        loadReports();
    }

    private void loadReports() {
        ApiService.getReportService().getPendingReports().enqueue(new Callback<List<ReportViewResponse>>() {
            @Override
            public void onResponse(Call<List<ReportViewResponse>> call, Response<List<ReportViewResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reportList.clear();
                    reportList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load reports", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReportViewResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshReports() {
        loadReports();
    }
}
