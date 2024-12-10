package com.example.eventplanner.activities.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;

import com.example.eventplanner.R;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.fragments.home.ProfileFragment;
import com.example.eventplanner.fragments.service_product.create_product.CreateProductFragment;
import com.example.eventplanner.fragments.home.HomeFragment;
import com.example.eventplanner.fragments.notification.NotificationFragment;
import com.example.eventplanner.fragments.service_product_provider.SeeMyProductsFragment;
import com.example.eventplanner.helpers.DrawerSetupTool;
import com.example.eventplanner.helpers.FragmentsTool;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.home_activity);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        DrawerSetupTool.setupDrawer(this, drawerLayout, navigationView, toolbar);
        navigationView.setNavigationItemSelectedListener(this);


        openChat();
    }
    private void openChat(){
        ImageView chatBubble= findViewById(R.id.chat_icon);
        chatBubble.setOnClickListener(v -> {
            ChatDialogFragment chatDialog = ChatDialogFragment.newInstance();
            chatDialog.show(getSupportFragmentManager(), "ChatDialog");
        });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override

    public boolean onNavigationItemSelected(@NotNull MenuItem item) {

        if(item.getItemId() == R.id.nav_home){
            FragmentsTool.to(new HomeFragment(), HomeActivity.this, false);
        }
        else if (item.getItemId() == R.id.nav_profile) {
            FragmentsTool.to(new ProfileFragment(), HomeActivity.this, false);
        }
        else if(item.getItemId()==R.id.nav_notification) {
            FragmentsTool.to(new NotificationFragment(), HomeActivity.this);
        }
        else if(item.getItemId()==R.id.nav_belongings){
            FragmentsTool.to(new SeeMyProductsFragment(), HomeActivity.this, false);
        }
        else if (item.getItemId() == R.id.nav_add) {
            //FragmentsTool.to(new AddEventTypeFragment(), HomeActivity.this, false);
            FragmentsTool.to(new CreateProductFragment(), HomeActivity.this, false);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}