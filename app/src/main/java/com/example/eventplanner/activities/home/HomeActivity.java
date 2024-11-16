package com.example.eventplanner.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.fragments.ChatDialogFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        openChat();
        eventDetails();

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
}