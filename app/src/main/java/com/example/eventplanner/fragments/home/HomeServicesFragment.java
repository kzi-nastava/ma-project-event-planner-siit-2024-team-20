package com.example.eventplanner.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eventplanner.R;
import com.example.eventplanner.helpers.FilterMenuManager;
import com.example.eventplanner.helpers.SortMenuManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeServicesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeServicesFragment newInstance(String param1, String param2) {
        HomeServicesFragment fragment = new HomeServicesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sort i Filter menadžeri
        SortMenuManager sortMenuManager = new SortMenuManager(requireContext());
        FilterMenuManager filterMenuManager = new FilterMenuManager(requireContext());

        ImageView sortServicesProductsButton= view.findViewById(R.id.sort_products);
        sortServicesProductsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            sortMenuManager.showServicesProductsSortMenu(sortServicesProductsButton);
        });

        ImageView filterServicesProductsButton= view.findViewById(R.id.filter_products);
        filterServicesProductsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            filterMenuManager.showFilterServicesProductsMenu(filterServicesProductsButton);
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_services, container, false);
    }
}