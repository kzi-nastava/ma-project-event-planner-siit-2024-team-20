package com.example.eventplanner.fragments.admin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.eventplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEventTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEventTypeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddEventTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEventTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEventTypeFragment newInstance(String param1, String param2) {
        AddEventTypeFragment fragment = new AddEventTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event_type, container, false);

        Button dropdownButton = view.findViewById(R.id.button3);

        String[] items = {"Option 1", "Option 2", "Option 3", "Option 4"};
        boolean[] selectedItems = new boolean[items.length];

        dropdownButton.setOnClickListener(v -> showChecklistPopup(v, items, selectedItems));

        return view;
    }
    private void showChecklistPopup(View anchorView, String[] items, boolean[] selectedItems) {
        LinearLayout popupLayout = new LinearLayout(getContext());
        popupLayout.setOrientation(LinearLayout.VERTICAL);
        popupLayout.setPadding(16, 16, 16, 16);

        for (int i = 0; i < items.length; i++) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(items[i]);
            checkBox.setChecked(selectedItems[i]);

            int index = i;
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> selectedItems[index] = isChecked);

            popupLayout.addView(checkBox);
        }
        Context context = getContext();
        PopupWindow popupWindow = new PopupWindow(popupLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.background_color)));

        popupWindow.showAsDropDown(anchorView);
    }
}