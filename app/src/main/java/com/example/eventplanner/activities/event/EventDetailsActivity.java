package com.example.eventplanner.activities.event;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.model.homepage.EventHomeResponse;

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
        EventHomeResponse event = (EventHomeResponse) intent.getSerializableExtra("event");

        if (event != null) {
            // Postavi podatke
        }
    }
    private void openChat(){
        ImageView chatBubble= findViewById(R.id.chat_icon);
        chatBubble.setOnClickListener(v -> {
            ChatDialogFragment chatDialog = ChatDialogFragment.newInstance();
            chatDialog.show(getSupportFragmentManager(), "ChatDialog");
        });
    }

}