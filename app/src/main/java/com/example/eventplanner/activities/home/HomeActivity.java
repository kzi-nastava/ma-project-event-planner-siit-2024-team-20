package com.example.eventplanner.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
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
        showEventSortMenu();
        showServicesProductsSortMenu();
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
    private void showEventSortMenu(){
        // Poveži dugme koje će otvoriti PopupWindow
        ImageView sortButton = findViewById(R.id.sort_events);

        sortButton.setOnClickListener(v -> {
            // Inflating layout za PopupWindow
            View popupView = getLayoutInflater().inflate(R.layout.sort_events_menu, null);
            // Konvertovanje dp u px za širinu
            int widthInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
            // Kreiranje PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView,
                    widthInPx,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true); // Daje mogućnost da se zatvori klikom van prozora

            // Pozicioniranje PopupWindow ispod dugmeta
            popupWindow.showAsDropDown(v, 0, 0);  // Pozicionira PopupWindow odmah ispod dugmeta

            // Logika za sortiranje (ASC/DESC)
            RadioGroup radioGroup = popupView.findViewById(R.id.sort_radio_group);
            RadioButton radioAsc = popupView.findViewById(R.id.radio_sort_asc);
            RadioButton radioDesc = popupView.findViewById(R.id.radio_sort_desc);

            // Setovanje početne selekcije (ako je potrebno)
            radioAsc.setChecked(true);  // Ako je podrazumevani ASC

            // Logika za višestruki izbor (CheckBox)
            CheckBox checkOption1 = popupView.findViewById(R.id.check_option_1);
            CheckBox checkOption2 = popupView.findViewById(R.id.check_option_2);
            CheckBox checkOption3 = popupView.findViewById(R.id.check_option_3);

            // Dugme za potvrdu
            Button confirmButton = popupView.findViewById(R.id.btn_sort_confirm);
            confirmButton.setOnClickListener(view -> {
                /* Provera šta je selektovano
                String sortOrder = radioAsc.isChecked() ? "Ascending" : "Descending";
                String selectedOptions = "";
                if (checkOption1.isChecked()) selectedOptions += "Option 1, ";
                if (checkOption2.isChecked()) selectedOptions += "Option 2, ";
                if (checkOption3.isChecked()) selectedOptions += "Option 3, ";
                // Zatvori PopupWindow*/
                popupWindow.dismiss();
            });
        });
        }
    private void showServicesProductsSortMenu(){
        // Poveži dugme koje će otvoriti PopupWindow
        ImageView sortButton = findViewById(R.id.sort_products);

        sortButton.setOnClickListener(v -> {
            // Inflating layout za PopupWindow
            View popupView = getLayoutInflater().inflate(R.layout.sort_services_products_menu, null);
            // Konvertovanje dp u px za širinu
            int widthInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
            // Kreiranje PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView,
                    widthInPx,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    true); // Daje mogućnost da se zatvori klikom van prozora

            // Pozicioniranje PopupWindow ispod dugmeta
            popupWindow.showAsDropDown(v, 0, 0);  // Pozicionira PopupWindow odmah ispod dugmeta

            // Logika za sortiranje (ASC/DESC)
            RadioGroup radioGroup = popupView.findViewById(R.id.sort_radio_group);
            RadioButton radioAsc = popupView.findViewById(R.id.radio_sort_asc);
            RadioButton radioDesc = popupView.findViewById(R.id.radio_sort_desc);
            RadioGroup choose=popupView.findViewById(R.id.choose_group);
            RadioButton radioService = popupView.findViewById(R.id.radio_service);
            RadioButton radioProduct = popupView.findViewById(R.id.radio_product);
            // Setovanje početne selekcije (ako je potrebno)
            radioAsc.setChecked(true);  // Ako je podrazumevani ASC
            radioService.setChecked(true);
            // Logika za višestruki izbor (CheckBox)
            CheckBox checkOption1 = popupView.findViewById(R.id.check_option_1);
            CheckBox checkOption2 = popupView.findViewById(R.id.check_option_2);
            CheckBox checkOption3 = popupView.findViewById(R.id.check_option_3);

            // Dugme za potvrdu
            Button confirmButton = popupView.findViewById(R.id.btn_sort_confirm);
            confirmButton.setOnClickListener(view -> {
                popupWindow.dismiss();
            });
        });
    }
    }

