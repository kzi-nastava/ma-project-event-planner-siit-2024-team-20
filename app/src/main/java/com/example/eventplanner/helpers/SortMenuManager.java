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
    public void showEventSortMenu(View v, List<String> selectedSortCriteria, String selectedSortOrder) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.sort_events_menu, null);
        int widthInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 250, context.getResources().getDisplayMetrics());
        int heightInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300, context.getResources().getDisplayMetrics());

        PopupWindow popupWindow = new PopupWindow(popupView,
                widthInPx,
                heightInPx,
                true);

        popupWindow.showAsDropDown(v, 0, 0);
        RadioButton radioAsc = popupView.findViewById(R.id.radio_sort_asc);
        RadioButton radioDesc = popupView.findViewById(R.id.radio_sort_desc);

        // Postavljanje prethodnog izbora
        if ("desc".equalsIgnoreCase(selectedSortOrder)) {
            radioDesc.setChecked(true);
        } else {
            radioAsc.setChecked(true);
        }

        ((CheckBox) popupView.findViewById(R.id.name_option)).setChecked(selectedSortCriteria.contains("name"));
        ((CheckBox) popupView.findViewById(R.id.description_option)).setChecked(selectedSortCriteria.contains("description"));
        ((CheckBox) popupView.findViewById(R.id.location_option)).setChecked(selectedSortCriteria.contains("locationName"));
        ((CheckBox) popupView.findViewById(R.id.type_option)).setChecked(selectedSortCriteria.contains("eventType.name"));
        ((CheckBox) popupView.findViewById(R.id.date_option)).setChecked(selectedSortCriteria.contains("startDate"));

        Button confirmButton = popupView.findViewById(R.id.btn_sort);
        confirmButton.setOnClickListener(view -> {
            String sortOrder = radioAsc.isChecked() ? "asc" : "desc";

            List<String> sortCriteria = new ArrayList<>();
            if (((CheckBox) popupView.findViewById(R.id.name_option)).isChecked()) sortCriteria.add("name");
            if (((CheckBox) popupView.findViewById(R.id.description_option)).isChecked()) sortCriteria.add("description");
            if (((CheckBox) popupView.findViewById(R.id.location_option)).isChecked()) sortCriteria.add("locationName");
            if (((CheckBox) popupView.findViewById(R.id.type_option)).isChecked()) sortCriteria.add("eventType.name");
            if (((CheckBox) popupView.findViewById(R.id.date_option)).isChecked()) sortCriteria.add("startDate");

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
            radioAsc.setChecked(true);
            // Dugme za potvrdu
            Button confirmButton = popupView.findViewById(R.id.btn_sort);
            confirmButton.setOnClickListener(view -> {
            String sortOrder = radioAsc.isChecked() ? "asc" : "desc";

            List<String> sortCriteria = new ArrayList<>();
            if (((CheckBox) popupView.findViewById(R.id.check_name)).isChecked()) sortCriteria.add("name");
            if (((CheckBox) popupView.findViewById(R.id.check_category)).isChecked()) sortCriteria.add("category");
            if (((CheckBox) popupView.findViewById(R.id.check_description)).isChecked()) sortCriteria.add("description");
            if (((CheckBox) popupView.findViewById(R.id.check_price)).isChecked()) sortCriteria.add("price");
            if (((CheckBox) popupView.findViewById(R.id.check_discount)).isChecked()) sortCriteria.add("discount");

            if (serviceListener!= null) {
                serviceListener.onSortSelected(sortCriteria, sortOrder);
            }

            popupWindow.dismiss();
        });



    }
    private SortSelectionListener listener;
    private SortServiceProductSelectionListener serviceListener;
    public void setSortSelectionListener(SortSelectionListener listener) {
        this.listener = listener;
    }
    public void setSortSelectionListener(SortServiceProductSelectionListener listener) {
        this.serviceListener= listener;
    }

}
