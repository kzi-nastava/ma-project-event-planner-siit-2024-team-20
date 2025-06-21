package com.example.eventplanner.helpers;


import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.time.LocalDate;

import com.example.eventplanner.R;
import com.example.eventplanner.model.eventDetails.FilterEventResponse;
import com.example.eventplanner.services.spec.ApiService;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterMenuManager {

    private Context context; // Držimo kontekst aktivnosti

    // Konstruktor koji prima context (aktivnost) i view (dugme)
    public FilterMenuManager(Context context) {
        this.context = context;
    }

    // Metoda za prikazivanje filter menija
    public void showFilterEventsMenu(View anchorView,List<String> selectedTypes,
                                     List<String> selectedCities,
                                     String dateAfter,
                                     String dateBefore) {
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
        LinearLayout typeLayout = popupView.findViewById(R.id.filter_option_type_layout);
        ImageView typeArrow = popupView.findViewById(R.id.filter_option_type_arrow);

        typeLayout.setOnClickListener(v -> {
            if (filterOptionTypeCheckboxList.getVisibility() == View.GONE) {
                filterOptionTypeCheckboxList.setVisibility(View.VISIBLE);
                typeArrow.setRotation(180);
            } else {
                filterOptionTypeCheckboxList.setVisibility(View.GONE);
                typeArrow.setRotation(0);
            }
        });

        LinearLayout locationLayout = popupView.findViewById(R.id.filter_option_location_layout);
        ImageView locationArrow = popupView.findViewById(R.id.filter_option_location_arrow);

        locationLayout.setOnClickListener(v -> {
            if (filterOptionLocationCheckboxList.getVisibility() == View.GONE) {
                filterOptionLocationCheckboxList.setVisibility(View.VISIBLE);
                locationArrow.setRotation(180);
            } else {
                filterOptionLocationCheckboxList.setVisibility(View.GONE);
                locationArrow.setRotation(0);
            }
        });



        ApiService.getEventService().getFilterOptions().enqueue(new Callback<FilterEventResponse>() {
            @Override
            public void onResponse(Call<FilterEventResponse> call, Response<FilterEventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FilterEventResponse filterOptions = response.body();

                    // Dinamičko dodavanje checkBox-ova za tipove
                    for (String type : filterOptions.getTypeOptions()) {
                        CheckBox checkBox = new CheckBox(context);
                        checkBox.setText(type);
                        if (selectedTypes != null && selectedTypes.contains(type)) {
                            checkBox.setChecked(true);
                        }
                        filterOptionTypeCheckboxList.addView(checkBox);
                    }

                    // Dinamičko dodavanje checkBox-ova za gradove
                    for (String location : filterOptions.getLocationOptions()) {
                        CheckBox checkBox = new CheckBox(context);
                        checkBox.setText(location);
                        if (selectedCities != null && selectedCities.contains(location)) {
                            checkBox.setChecked(true);
                        }
                        filterOptionLocationCheckboxList.addView(checkBox);
                    }

                } else {
                    Toast.makeText(context, "Failed to load filter options", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterEventResponse> call, Throwable t) {
                Toast.makeText(context, "Error loading filter options: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // Lista za dana, meseca, godine u Date Before i Date After
        Spinner spinnerDayBefore = popupView.findViewById(R.id.spinnerDayBefore);
        Spinner spinnerMonthBefore = popupView.findViewById(R.id.spinnerMonthBefore);
        Spinner spinnerYearBefore = popupView.findViewById(R.id.spinnerYearBefore);

        AtomicReference<Spinner> spinnerDayAfter = new AtomicReference<>(popupView.findViewById(R.id.spinnerDayAfter));
        AtomicReference<Spinner> spinnerMonthAfter = new AtomicReference<>(popupView.findViewById(R.id.spinnerMonthAfter));
        AtomicReference<Spinner> spinnerYearAfter = new AtomicReference<>(popupView.findViewById(R.id.spinnerYearAfter));

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
        spinnerDayAfter.get().setAdapter(dayAdapter);
        spinnerMonthAfter.get().setAdapter(monthAdapter);
        spinnerYearAfter.get().setAdapter(yearAdapter);

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
            List<String> newSelectedTypes = new ArrayList<>();
            List<String> newSelectedLocations = new ArrayList<>();

            for (int i = 0; i < filterOptionTypeCheckboxList.getChildCount(); i++) {
                View child = filterOptionTypeCheckboxList.getChildAt(i);
                if (child instanceof CheckBox && ((CheckBox) child).isChecked()) {
                    newSelectedTypes.add(((CheckBox) child).getText().toString());
                }
            }

            for (int i = 0; i < filterOptionLocationCheckboxList.getChildCount(); i++) {
                View child = filterOptionLocationCheckboxList.getChildAt(i);
                if (child instanceof CheckBox && ((CheckBox) child).isChecked()) {
                    newSelectedLocations.add(((CheckBox) child).getText().toString());
                }
            }

            String newDateAfter = null;
            String newDateBefore = null;

            if (checkboxDateAfter.isChecked()) {
                newDateAfter = formatDateFromSpinners(spinnerYearAfter.get(), spinnerMonthAfter.get(), spinnerDayAfter.get());
            }
            if (checkboxDateBefore.isChecked()) {
                newDateBefore = formatDateFromSpinners(spinnerYearBefore, spinnerMonthBefore, spinnerDayBefore);
            }
            if (dateAfter != null) {
                checkboxDateAfter.setChecked(true);
                dateAfterContainer.setVisibility(View.VISIBLE);
                setSpinnerDateFromString(dateAfter, spinnerYearAfter.get(), spinnerMonthAfter.get(), spinnerDayAfter.get());
            }

            if (dateBefore != null) {
                checkboxDateBefore.setChecked(true);
                dateBeforeContainer.setVisibility(View.VISIBLE);
                setSpinnerDateFromString(dateBefore, spinnerYearBefore, spinnerMonthBefore, spinnerDayBefore);
            }

            if (filterListener != null) {
                filterListener.onFilterSelected(newSelectedTypes, newSelectedLocations, newDateAfter, newDateBefore);
            }

            popupWindow.dismiss();
        });


    }
    private String formatDateFromSpinners(Spinner year, Spinner month, Spinner day) {
        int y = Integer.parseInt((String) year.getSelectedItem());
        int m = month.getSelectedItemPosition() + 1; // jer spinner je 0-based za mesece
        int d = Integer.parseInt((String) day.getSelectedItem());
        return String.format(Locale.US, "%04d-%02d-%02d", y, m, d);
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
    private FilterSelectionListener filterListener;
    public void setFilterSelectionListener(FilterSelectionListener listener) {
        this.filterListener = listener;
    }

    private void setSpinnerDateFromString(String dateStr, Spinner yearSpinner, Spinner monthSpinner, Spinner daySpinner) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            ArrayAdapter<String> yearAdapter = (ArrayAdapter<String>) yearSpinner.getAdapter();
            ArrayAdapter<String> monthAdapter = (ArrayAdapter<String>) monthSpinner.getAdapter();
            ArrayAdapter<String> dayAdapter = (ArrayAdapter<String>) daySpinner.getAdapter();

            yearSpinner.setSelection(yearAdapter.getPosition(String.valueOf(date.getYear())));
            monthSpinner.setSelection(date.getMonthValue() - 1); // 0-based
            daySpinner.setSelection(date.getDayOfMonth() - 1);   // 0-based
        } catch (Exception e) {
            // ignoriši ako je loš format
            e.printStackTrace();
        }
    }
}

