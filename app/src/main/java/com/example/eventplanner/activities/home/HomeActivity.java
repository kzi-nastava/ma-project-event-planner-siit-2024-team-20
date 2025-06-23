package com.example.eventplanner.activities.home;

import android.content.Intent;
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
import com.example.eventplanner.activities.startup.LoginActivity;
import com.example.eventplanner.fragments.admin.AddEventTypeFragment;
import com.example.eventplanner.fragments.admin.CommentManagementFragment;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.fragments.event.create_event.CreateEventFragment;
import com.example.eventplanner.fragments.home.ProfileFragment;
import com.example.eventplanner.fragments.home.HomeFragment;
import com.example.eventplanner.fragments.notification.NotificationFragment;
import com.example.eventplanner.fragments.service_product.create_product.CreateProductFragment;
import com.example.eventplanner.fragments.service_product_provider.SeeMyProductsFragment;
import com.example.eventplanner.helpers.DrawerSetupTool;
import com.example.eventplanner.helpers.FragmentsTool;
import com.example.eventplanner.services.spec.AuthService;
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
        String role = AuthService.getRoleFromToken();
        MenuItem belongingsItem = navigationView.getMenu().findItem(R.id.nav_belongings);
        if (belongingsItem != null) {
            belongingsItem.setVisible("ROLE_SERVICE_PRODUCT_PROVIDER".equals(role));
        }

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
            FragmentsTool.to(new NotificationFragment(), HomeActivity.this,false);
        }
        else if (item.getItemId() == R.id.nav_add) {
            String role = AuthService.getRoleFromToken();

            if (role != null && role.equals("ROLE_ADMIN")) {
                FragmentsTool.to(new AddEventTypeFragment(), HomeActivity.this, false);
            }
            else if(role != null && role.equals("ROLE_EVENT_ORGANIZER")){
                FragmentsTool.to(new CreateEventFragment(), HomeActivity.this, false);
            }else if(role != null && role.equals("ROLE_SERVICE_PRODUCT_PROVIDER")){
                FragmentsTool.to(new CreateProductFragment(), HomeActivity.this, false);
            }
        }
        else if(item.getItemId()==R.id.nav_edit_comments) {
            FragmentsTool.to(new CommentManagementFragment(),HomeActivity.this,false);
        } else if (item.getItemId() == R.id.nav_belongings) {
            FragmentsTool.to(new SeeMyProductsFragment(), HomeActivity.this, false);
        } else if(item.getItemId()==R.id.nav_logout){
            AuthService.logout();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}