package com.example.eventplanner.helpers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.eventplanner.fragments.home.HomeEventsFragment;
import com.example.eventplanner.fragments.home.HomeServicesFragment;

public class HomePagerAdapter extends FragmentStateAdapter {

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeEventsFragment();
            case 1:
                return new HomeServicesFragment();
            default:
                return new HomeEventsFragment(); // Početni fragment
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Imamo 2 taba: Events i Services
    }
}

