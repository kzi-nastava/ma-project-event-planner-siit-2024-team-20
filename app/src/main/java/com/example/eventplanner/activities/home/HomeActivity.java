package com.example.eventplanner.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.event.EventDetailsActivity;
import com.example.eventplanner.activities.service_product.ServiceProductDetailsActivity;
import com.example.eventplanner.fragments.chat.ChatDialogFragment;
import com.example.eventplanner.helpers.FilterMenuManager;
import com.example.eventplanner.helpers.SortMenuManager;

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
        SortMenuManager sortMenuManager=new SortMenuManager(HomeActivity.this);
        ImageView sortEventsButton= findViewById(R.id.sort_events);
        sortEventsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            sortMenuManager.showEventSortMenu(sortEventsButton);
        });

        ImageView sortServicesProductsButton= findViewById(R.id.sort_products);
        sortServicesProductsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            sortMenuManager.showServicesProductsSortMenu(sortServicesProductsButton);
        });

        FilterMenuManager filterMenuManager = new FilterMenuManager(HomeActivity.this);
        ImageView filterEventsButton= findViewById(R.id.filter_events);
        filterEventsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            filterMenuManager.showFilterEventsMenu(filterEventsButton);
        });

        ImageView filterServicesProductsButton= findViewById(R.id.filter_products);
        filterServicesProductsButton.setOnClickListener(v -> {
            // Prikazivanje filter menija
            filterMenuManager.showFilterServicesProductsMenu(filterServicesProductsButton);
        });
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