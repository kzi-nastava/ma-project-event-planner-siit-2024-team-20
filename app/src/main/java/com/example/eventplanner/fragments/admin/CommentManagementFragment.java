package com.example.eventplanner.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.adapters.CommentAdapter;
import com.example.eventplanner.model.entities.Comment;
import com.example.eventplanner.model.review.CommentResponse;
import com.example.eventplanner.services.spec.ApiService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<CommentResponse> comments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_management, container, false);

        recyclerView = view.findViewById(R.id.rv_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CommentAdapter(comments, new CommentAdapter.OnCommentActionListener() {
            @Override
            public void onApprove(CommentResponse comment) {
                approveComment(comment.getId());
            }

            @Override
            public void onDelete(CommentResponse comment) {
                deleteComment(comment.getId());
            }
        });

        recyclerView.setAdapter(adapter);

        fetchPendingComments();

        return view;
    }

    private void fetchPendingComments() {
        ApiService.getCommentService().getPendingComments().enqueue(new Callback<List<CommentResponse>>() {
            @Override
            public void onResponse(Call<List<CommentResponse>> call, Response<List<CommentResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CommentResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void approveComment(Long id) {
        ApiService.getCommentService().approveComment(id).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    fetchPendingComments();
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void deleteComment(Long id) {
        ApiService.getCommentService().deleteComment(id).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    fetchPendingComments();
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}


