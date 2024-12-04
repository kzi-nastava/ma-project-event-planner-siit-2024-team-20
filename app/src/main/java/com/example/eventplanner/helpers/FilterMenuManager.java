package com.example.eventplanner.helpers;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.eventplanner.R;

import java.time.LocalDate;
import java.util.Locale;

public class FilterMenuManager {

    private Context context; // Držimo kontekst aktivnosti

    // Konstruktor koji prima context (aktivnost) i view (dugme)
    public FilterMenuManager(Context context) {
        this.context = context;
    }

    // Metoda za prikazivanje filter menija
    public void showFilterEventsMenu(View anchorView) {
        // Inflating custom layout za PopupWindow
        View popupView = LayoutInflater.from(context).inflate(R.layout.filter_events_menu, null);
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
        popupWindow.showAsDropDown(anchorView, 0, 0);

        // Inicijalizacija stavki za filtriranje
        LinearLayout filterOption1Layout = popupView.findViewById(R.id.filter_option_1_layout);
        final LinearLayout filterOption1CheckboxList = popupView.findViewById(R.id.filter_option_1_checkbox_list);
        final ImageView filterOption1Arrow = popupView.findViewById(R.id.filter_option_1_arrow);

        // Kada korisnik klikne na opciju, otvoriće se lista sa checkbox-ovima
        filterOption1Layout.setOnClickListener(view -> {
            if (filterOption1CheckboxList.getVisibility() == View.GONE) {
                filterOption1CheckboxList.setVisibility(View.VISIBLE);
                filterOption1Arrow.setRotation(180); // Okreće strelicu kada se lista otvori
            } else {
                filterOption1CheckboxList.setVisibility(View.GONE);
                filterOption1Arrow.setRotation(0); // Okreće strelicu kada se lista zatvori
            }
        });

        // Inicijalizacija checkbox stavki
        CheckBox checkOption1 = popupView.findViewById(R.id.check_option_1);
        CheckBox checkOption2 = popupView.findViewById(R.id.check_option_2);
        CheckBox checkOption3 = popupView.findViewById(R.id.check_option_3);

        // Dugme za povratak (zatvaranje pop-up prozora)
        Button btnFilter= popupView.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(view -> {
            // Kada klikneš na dugme, zatvaraš popup
            popupWindow.dismiss();
        });
        Button filterByDateButton = popupView.findViewById(R.id.filter_by_date_button);
        TextView dateText=popupView.findViewById(R.id.date_text);
        filterByDateButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    popupView.getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Ovdje možete upisati datum u TextView ili neko drugo polje
                            String selectedDate = String.format(Locale.ENGLISH, "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);
                            dateText.setText(selectedDate);
                        }
                    },
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonthValue() - 1,
                    LocalDate.now().getDayOfMonth()
            );
            datePickerDialog.show();
        });
    }
    public void showFilterServicesProductsMenu(View anchorView) {
        // Inflating custom layout za PopupWindow
        View popupView = LayoutInflater.from(context).inflate(R.layout.filter_services_products_menu, null);
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
        popupWindow.showAsDropDown(anchorView, 0, 0);

        // Inicijalizacija stavki za filtriranje
        LinearLayout filterOption1Layout = popupView.findViewById(R.id.filter_option_1_layout);
        final LinearLayout filterOption1CheckboxList = popupView.findViewById(R.id.filter_category_checkbox_list);
        final ImageView filterOption1Arrow = popupView.findViewById(R.id.filter_option_1_arrow);

        // Kada korisnik klikne na opciju, otvoriće se lista sa checkbox-ovima
        filterOption1Layout.setOnClickListener(view -> {
            if (filterOption1CheckboxList.getVisibility() == View.GONE) {
                filterOption1CheckboxList.setVisibility(View.VISIBLE);
                filterOption1Arrow.setRotation(180); // Okreće strelicu kada se lista otvori
            } else {
                filterOption1CheckboxList.setVisibility(View.GONE);
                filterOption1Arrow.setRotation(0); // Okreće strelicu kada se lista zatvori
            }
        });

        // Inicijalizacija checkbox stavki
        CheckBox checkOption1 = popupView.findViewById(R.id.check_option_1);
        CheckBox checkOption2 = popupView.findViewById(R.id.check_option_2);
        CheckBox checkOption3 = popupView.findViewById(R.id.check_option_3);
        RadioButton service=popupView.findViewById(R.id.radio_service);
        RadioButton product=popupView.findViewById(R.id.radio_product);
        service.setChecked(true);
        // Dugme za povratak (zatvaranje pop-up prozora)
        Button btnFilter = popupView.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(view -> {
            // Kada klikneš na dugme, zatvaraš popup
            popupWindow.dismiss();
        });

    }
}
