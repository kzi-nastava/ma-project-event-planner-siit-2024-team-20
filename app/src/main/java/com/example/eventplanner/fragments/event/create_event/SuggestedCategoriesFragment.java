package com.example.eventplanner.fragments.event.create_event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventplanner.R;
import com.example.eventplanner.helpers.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuggestedCategoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<String> categoryList = new ArrayList<>();

    private TextView titleText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_suggested_categories, container, false);

        titleText = view.findViewById(R.id.text_suggested_categories_title);

        recyclerView = view.findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CategoryAdapter(getContext(), categoryList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setCategories(@NonNull List<String> categories) {
        categoryList.clear();
        categoryList.addAll(categories);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (titleText != null) {
            titleText.setVisibility(View.VISIBLE);
        }
    }
}
