package com.example.eventplanner.activities.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.eventplanner.activities.home.HomeActivity;
import com.example.eventplanner.activities.service_product.BookingActivity;
import com.example.eventplanner.activities.startup.LoginActivity;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.helpers.DrawerSetupTool;
import com.example.eventplanner.helpers.StatusLineTool;
import com.example.eventplanner.model.EventHome;
import com.google.android.material.navigation.NavigationView;

public class EventDetailsActivity extends AppCompatActivity{
    private TextView eventName;
    private TextView eventDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.event_details_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView returnBack = findViewById(R.id.return_back);
        returnBack.setOnClickListener(v -> onBackPressed());

        openChat();
        eventName = findViewById(R.id.title);
        eventDescription = findViewById(R.id.description);

        Intent intent = getIntent();
        //promeni tip u onaj za detalje
        EventHome event = (EventHome) intent.getSerializableExtra("event");

        if (event != null) {
            // Postavi podatke
        }
    }
    private void openChat(){
        ImageView chatBubble= findViewById(R.id.chat_icon);
        chatBubble.setOnClickListener(v -> {
            // Otvaranje Chat dijaloga
            ChatDialogFragment chatDialog = ChatDialogFragment.newInstance();
            chatDialog.show(getSupportFragmentManager(), "ChatDialog");
        });
    }

}