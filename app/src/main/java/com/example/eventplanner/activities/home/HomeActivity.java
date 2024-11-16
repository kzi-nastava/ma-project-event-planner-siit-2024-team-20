package com.example.eventplanner.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

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
        ImageView sortButton = findViewById(R.id.sort_events);

        // Poziv funkcije za prikazivanje Popup menija
        sortButton.setOnClickListener(v -> showSortMenu(v));

    }
    // Funkcija za prikazivanje menija
    private void showSortMenu(View anchorView) {
        // Kreiranje popup menija
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());

        // Prikazivanje menija ispod dugmeta
        popupMenu.show();

        // Postavljanje logike za selektovanje
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.option_sort_asc) {
                item.setChecked(true);
                popupMenu.getMenu().findItem(R.id.option_sort_desc).setChecked(false);
            } else if (item.getItemId() == R.id.option_sort_desc) {
                item.setChecked(true);
                popupMenu.getMenu().findItem(R.id.option_sort_asc).setChecked(false);
            } else if (item.getItemId() == R.id.option_check_1) {
                item.setChecked(!item.isChecked());
            } else if (item.getItemId() == R.id.option_check_2) {
                item.setChecked(!item.isChecked());
            } else if (item.getItemId() == R.id.option_check_3) {
                item.setChecked(!item.isChecked());
            }
            return true;
        });
    }

}