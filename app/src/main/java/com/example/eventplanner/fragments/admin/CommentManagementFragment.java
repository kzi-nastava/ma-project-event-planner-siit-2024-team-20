package com.example.eventplanner.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.adapters.CommentAdapter;
import com.example.eventplanner.model.entities.Comment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CommentManagementFragment extends Fragment {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<Comment> comments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_management, container, false);

        // Inicijalizacija RecyclerView
        recyclerView = view.findViewById(R.id.rv_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicijalizacija podataka
        comments = new ArrayList<>();
        comments.add(new Comment(1L, "This is a comment", "User1", "service"));
        comments.add(new Comment(2L, "Another comment", "User2", "product"));
        comments.add(new Comment(3L, "Yet another comment", "User3", "event"));

        // Povezivanje adaptera
        adapter = new CommentAdapter(comments);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
