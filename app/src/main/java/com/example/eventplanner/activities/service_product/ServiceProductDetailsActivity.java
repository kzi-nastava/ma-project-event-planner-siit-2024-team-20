package com.example.eventplanner.activities.service_product;

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

import com.example.eventplanner.R;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.fragments.service_product.ServiceProductDetailFragment;
import com.example.eventplanner.helpers.DrawerSetupTool;
import com.example.eventplanner.helpers.StatusLineTool;
import com.google.android.material.navigation.NavigationView;

public class ServiceProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.service_product_details_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView returnBack = findViewById(R.id.return_back);
        returnBack.setOnClickListener(v -> onBackPressed());

        openChat();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ServiceProductDetailFragment())
                    .commit();
        }
        String eventId = getIntent().getStringExtra("product_id");
    }
    private void openChat(){
        ImageView chatBubble= findViewById(R.id.chat_icon);
        chatBubble.setOnClickListener(v -> {
            ChatDialogFragment chatDialog = ChatDialogFragment.newInstance();
            chatDialog.show(getSupportFragmentManager(), "ChatDialog");
        });
    }
}