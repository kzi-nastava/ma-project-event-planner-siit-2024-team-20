package com.example.eventplanner.helpers;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.google.android.material.slider.RangeSlider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        LinearLayout filterOptionTypeCheckboxList = popupView.findViewById(R.id.filter_option_type_checkbox_list);
        LinearLayout filterOptionLocationCheckboxList = popupView.findViewById(R.id.filter_option_location_checkbox_list);

        List<String> typeOptions = Arrays.asList("Conference", "Workshop", "Meetup");
        for (String type : typeOptions) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(type);
            filterOptionTypeCheckboxList.addView(checkBox);
        }

        List<String> locationOptions = Arrays.asList("New York", "San Francisco", "Chicago");
        for (String location : locationOptions) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(location);
            filterOptionLocationCheckboxList.addView(checkBox);
        }

        // Lista za dana, meseca, godine u Date Before i Date After
        Spinner spinnerDayBefore = popupView.findViewById(R.id.spinnerDayBefore);
        Spinner spinnerMonthBefore = popupView.findViewById(R.id.spinnerMonthBefore);
        Spinner spinnerYearBefore = popupView.findViewById(R.id.spinnerYearBefore);

        Spinner spinnerDayAfter = popupView.findViewById(R.id.spinnerDayAfter);
        Spinner spinnerMonthAfter = popupView.findViewById(R.id.spinnerMonthAfter);
        Spinner spinnerYearAfter = popupView.findViewById(R.id.spinnerYearAfter);

        // Popuniti spinnere za dan, mesec i godinu
        String[] days = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] years = new String[]{"2023", "2024", "2025"};

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, days);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, years);

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDayBefore.setAdapter(dayAdapter);
        spinnerMonthBefore.setAdapter(monthAdapter);
        spinnerYearBefore.setAdapter(yearAdapter);
        spinnerDayAfter.setAdapter(dayAdapter);
        spinnerMonthAfter.setAdapter(monthAdapter);
        spinnerYearAfter.setAdapter(yearAdapter);

        // Date Before i Date After CheckBox logika
        CheckBox checkboxDateBefore = popupView.findViewById(R.id.checkboxDateBefore);
        CheckBox checkboxDateAfter = popupView.findViewById(R.id.checkboxDateAfter);

        final LinearLayout dateBeforeContainer = popupView.findViewById(R.id.dateBeforeContainer);
        final LinearLayout dateAfterContainer = popupView.findViewById(R.id.dateAfterContainer);

        checkboxDateBefore.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dateBeforeContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        checkboxDateAfter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dateAfterContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        Button btnFilter = popupView.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(view -> {
            // Kreiranje lista za čuvanje selektovanih opcija
            List<String> selectedTypes = new ArrayList<>();
            List<String> selectedLocations = new ArrayList<>();

            // Prolazak kroz sve checkbox-ove za Type
            for (int i = 0; i < filterOptionTypeCheckboxList.getChildCount(); i++) {
                View child = filterOptionTypeCheckboxList.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) child;
                    if (checkBox.isChecked()) {
                        selectedTypes.add(checkBox.getText().toString()); // Dodavanje selektovane opcije u listu
                    }
                }
            }

            // Prolazak kroz sve checkbox-ove za Location
            for (int i = 0; i < filterOptionLocationCheckboxList.getChildCount(); i++) {
                View child = filterOptionLocationCheckboxList.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) child;
                    if (checkBox.isChecked()) {
                        selectedLocations.add(checkBox.getText().toString()); // Dodavanje selektovane opcije u listu
                    }
                }
            }

            // Prikaz selektovanih opcija (ovo možete zameniti logikom za filtriranje)
            Toast.makeText(
                    context,
                    "Selected Types: " + selectedTypes + "\nSelected Locations: " + selectedLocations,
                    Toast.LENGTH_LONG
            ).show();

            popupWindow.dismiss(); // Zatvori popup
        });
    }
    public void showFilterServicesProductsMenu(View anchorView) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.filter_services_products_menu, null);
        int widthInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 250, context.getResources().getDisplayMetrics());
        int heightInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 350, context.getResources().getDisplayMetrics());
        // Kreiranje PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView,
                widthInPx,
                heightInPx,
                true); // Daje mogućnost da se zatvori klikom van prozora

        // Pozicioniranje PopupWindow ispod dugmeta
        popupWindow.showAsDropDown(anchorView, 0, 0);

        // Inicijalizacija stavki za filtriranje
        LinearLayout filterCategoryLayout = popupView.findViewById(R.id.filter_category_layout);
        LinearLayout filterCategoryCheckboxList = popupView.findViewById(R.id.filter_category_checkbox_list);
        ImageView filterCategoryArrow = popupView.findViewById(R.id.filter_category_arrow);

        // Dodavanje dinamičkih opcija za kategorije
        List<String> categoryOptions = Arrays.asList("Category 1", "Category 2", "Category 3");
        for (String category : categoryOptions) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(category);
            filterCategoryCheckboxList.addView(checkBox);
        }

        // Kada korisnik klikne na opciju, otvara/zatvara se lista sa checkbox-ovima
        filterCategoryLayout.setOnClickListener(view -> {
            if (filterCategoryCheckboxList.getVisibility() == View.GONE) {
                filterCategoryCheckboxList.setVisibility(View.VISIBLE);
                filterCategoryArrow.setRotation(180); // Rotacija strelice na dole
            } else {
                filterCategoryCheckboxList.setVisibility(View.GONE);
                filterCategoryArrow.setRotation(0); // Rotacija strelice na gore
            }
        });

        // Inicijalizacija RangeSlider-a za cene
        RangeSlider priceMinSlider = popupView.findViewById(R.id.price_min_slider);
        RangeSlider priceMaxSlider = popupView.findViewById(R.id.price_max_slider);

        priceMinSlider.addOnChangeListener((slider, value, fromUser) -> {
            // Ova funkcija se poziva svaki put kada se vrednost RangeSlider-a promeni
            float minPrice = slider.getValues().get(0); // Trenutna minimalna cena
            Log.d("Filter", "Min Price: " + minPrice);
        });

        priceMaxSlider.addOnChangeListener((slider, value, fromUser) -> {
            // Ova funkcija se poziva svaki put kada se vrednost RangeSlider-a promeni
            float maxPrice = slider.getValues().get(1); // Trenutna maksimalna cena
            Log.d("Filter", "Max Price: " + maxPrice);
        });

        // RadioButton za izbor između Service i Product
        RadioButton radioService = popupView.findViewById(R.id.radio_service);
        RadioButton radioProduct = popupView.findViewById(R.id.radio_product);

        // Postavljanje podrazumevanog izbora
        radioService.setChecked(true);

        // Dugme za filtriranje
        Button btnFilter = popupView.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(view -> {
            // Prikupljanje selektovanih kategorija
            List<String> selectedCategories = new ArrayList<>();
            for (int i = 0; i < filterCategoryCheckboxList.getChildCount(); i++) {
                View child = filterCategoryCheckboxList.getChildAt(i);
                if (child instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) child;
                    if (checkBox.isChecked()) {
                        selectedCategories.add(checkBox.getText().toString());
                    }
                }
            }

            // Prikupljanje vrednosti RangeSlider-a
            float minPrice = priceMinSlider.getValues().get(0);
            float maxPrice = priceMaxSlider.getValues().get(1);

            // Prikupljanje izbora između Service i Product
            String selectedType = radioService.isChecked() ? "Service" : "Product";

            // Prikaz odabranih filtera
            Toast.makeText(context,
                    "Type: " + selectedType +
                            "\nCategories: " + selectedCategories +
                            "\nPrice Range: " + minPrice + " - " + maxPrice,
                    Toast.LENGTH_LONG).show();

            // Zatvaranje popup prozora
            popupWindow.dismiss();
        });

    }
}
