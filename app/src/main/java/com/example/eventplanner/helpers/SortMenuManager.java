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
                /* Provera šta je selektovano
                String sortOrder = radioAsc.isChecked() ? "Ascending" : "Descending";
                String selectedOptions = "";
                if (checkOption1.isChecked()) selectedOptions += "Option 1, ";
                if (checkOption2.isChecked()) selectedOptions += "Option 2, ";
                if (checkOption3.isChecked()) selectedOptions += "Option 3, ";
                // Zatvori PopupWindow*/
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
}
