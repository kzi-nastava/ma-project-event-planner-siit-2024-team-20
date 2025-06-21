package com.example.eventplanner.helpers;

import android.content.Context;
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

import com.example.eventplanner.R;

import java.util.ArrayList;
import java.util.List;

public class SortMenuManager {
    private Context context; // Držimo kontekst aktivnosti

    // Konstruktor koji prima context (aktivnost) i view (dugme)
    public SortMenuManager(Context context) {
        this.context = context;
    }
    public void showEventSortMenu(View v){
            // Inflating layout za PopupWindow
            View popupView = LayoutInflater.from(context).inflate(R.layout.sort_events_menu, null);
            // Konvertovanje dp u px za širinu
            int widthInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 250, context.getResources().getDisplayMetrics());
            int heightInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());
            // Kreiranje PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView,
                    widthInPx,
                    heightInPx,
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
            /*CheckBox checkOption1 = popupView.findViewById(R.id.check_option_1);
            CheckBox checkOption2 = popupView.findViewById(R.id.check_option_2);
            CheckBox checkOption3 = popupView.findViewById(R.id.check_option_3);*/

            // Dugme za potvrdu
            Button confirmButton = popupView.findViewById(R.id.btn_sort);
            confirmButton.setOnClickListener(view -> {
            // 1. Odredi smer sortiranja
            String sortOrder = radioAsc.isChecked() ? "asc" : "desc";

            // 2. Sakupi sve izabrane kriterijume
            List<String> sortCriteria = new ArrayList<>();
            if (((CheckBox) popupView.findViewById(R.id.name_option)).isChecked()) sortCriteria.add("name");
            if (((CheckBox) popupView.findViewById(R.id.description_option)).isChecked()) sortCriteria.add("description");
            if (((CheckBox) popupView.findViewById(R.id.location_option)).isChecked()) sortCriteria.add("locationName");
            if (((CheckBox) popupView.findViewById(R.id.type_option)).isChecked()) sortCriteria.add("eventType.name");
            if (((CheckBox) popupView.findViewById(R.id.date_option)).isChecked()) sortCriteria.add("startDate");

            // 3. Pozovi callback koji zna šta da radi sa tim (npr. HomeEventsFragmen
                if (listener != null) {
                    listener.onSortSelected(sortCriteria, sortOrder);
                }


                popupWindow.dismiss();
        });

    }
    public void showServicesProductsSortMenu(View v){
            View popupView = LayoutInflater.from(context).inflate(R.layout.sort_services_products_menu, null);
            // Konvertovanje dp u px za širinu
            int widthInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 250, context.getResources().getDisplayMetrics());
            int heightInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());
            // Kreiranje PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView,
                    widthInPx,
                    heightInPx,
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
            /*CheckBox checkOption1 = popupView.findViewById(R.id.check_option_1);
            CheckBox checkOption2 = popupView.findViewById(R.id.check_option_2);
            CheckBox checkOption3 = popupView.findViewById(R.id.check_option_3);*/

            // Dugme za potvrdu
            Button confirmButton = popupView.findViewById(R.id.btn_sort);
            confirmButton.setOnClickListener(view -> {
                popupWindow.dismiss();
            });

    }
    private SortSelectionListener listener;

    public void setSortSelectionListener(SortSelectionListener listener) {
        this.listener = listener;
    }


}
