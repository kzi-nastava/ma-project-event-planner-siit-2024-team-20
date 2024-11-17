package com.example.eventplanner.helpers;

import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.eventplanner.R;
import com.google.android.material.navigation.NavigationView;

public class DrawerSetupTool {
    public static void setupDrawer(AppCompatActivity activity, DrawerLayout drawerLayout, NavigationView navigationView, View toolbar) {


        activity.setSupportActionBar((androidx.appcompat.widget.Toolbar) toolbar);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity,
                drawerLayout,
                (androidx.appcompat.widget.Toolbar) toolbar,
                R.string.nb_open,
                R.string.nb_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

}
