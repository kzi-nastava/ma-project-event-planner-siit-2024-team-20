package com.example.eventplanner.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.eventplanner.R;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.fragments.ProfileFragment;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.fragments.home.HomeFragment;
import com.example.eventplanner.helpers.DrawerSetupTool;
import com.google.android.material.navigation.NavigationView;

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
            // Otvaranje Chat dijaloga
            ChatDialogFragment chatDialog = ChatDialogFragment.newInstance();
            chatDialog.show(getSupportFragmentManager(), "ChatDialog");
        });
    }
    private void eventDetails(){
        // Unutar tvoje aktivnosti ili fragmenta
        View eventCard1 = findViewById(R.id.event_card_1);
        View eventTopCard1 = findViewById(R.id.event5_card_1);
        View productCard1 = findViewById(R.id.product_card_1);
        View productTopCard1 = findViewById(R.id.product5_card_1);
        eventCard1.setOnClickListener(v -> {
            // Otvoriti novu aktivnost sa detaljima
            Intent intent = new Intent(this, EventDetailsActivity.class);
            intent.putExtra("event_id", "1");  // Dodaj sve potrebne podatke kao extra
            startActivity(intent);
        });
        eventTopCard1.setOnClickListener(v -> {
            // Otvoriti novu aktivnost sa detaljima
            Intent intent = new Intent(this, EventDetailsActivity.class);
            intent.putExtra("event_id", "1");  // Dodaj sve potrebne podatke kao extra
            startActivity(intent);
        });
        productCard1.setOnClickListener(v -> {
            // Otvoriti novu aktivnost sa detaljima
            Intent intent = new Intent(this, ServiceProductDetailsActivity.class);
            intent.putExtra("product_id", "1");  // Dodaj sve potrebne podatke kao extra
            startActivity(intent);
        });
        productTopCard1.setOnClickListener(v -> {
            // Otvoriti novu aktivnost sa detaljima
            Intent intent = new Intent(this, ServiceProductDetailsActivity.class);
            intent.putExtra("product_id", "1");  // Dodaj sve potrebne podatke kao extra
            startActivity(intent);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    
}