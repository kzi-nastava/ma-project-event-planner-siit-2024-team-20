package com.example.eventplanner.fragments.event.create_event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.helpers.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SuggestedCategoriesFragment extends Fragment {


    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<String> categoryList;
    private String eventType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventType = getArguments().getString("event_type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggested_categories, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCategories();

        return view;
    }
    private void loadCategories() {
        //iz baze
        categoryList = new ArrayList<>();
        if ("option1".equals(eventType)) {
            categoryList.add("Category 1");
            categoryList.add("Category 2");
        } else if ("option2".equals(eventType)) {
            categoryList.add("Category A");
            categoryList.add("Category B");
        }

        adapter = new CategoryAdapter(getContext(), categoryList);
        recyclerView.setAdapter(adapter);
    }
}