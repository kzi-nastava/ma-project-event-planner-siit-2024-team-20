package com.example.eventplanner.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.example.eventplanner.helpers.HomePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.viewPager);

        // Kreiranje adaptera za ViewPager2
        pagerAdapter = new HomePagerAdapter(getActivity());
        viewPager.setAdapter(pagerAdapter);

        // Povezivanje TabLayout sa ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Events");
                    break;
                case 1:
                    tab.setText("Services");
                    break;
            }
        }).attach();

        return root;
    }
}