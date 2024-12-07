package com.example.eventplanner.fragments.service_product.create_product;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.example.eventplanner.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    MaterialCardView selectCard;
    TextView tvETypes;
    boolean [] selectedETypes;
    ArrayList<Integer> eTypesList = new ArrayList<>();
    String [] eTypesArray = {"option1", "option2", "option3"};

    private Switch switchChangeFragment;
    private NumberPicker npDiscount;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProductFragment newInstance(String param1, String param2) {
        CreateProductFragment fragment = new CreateProductFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_product, container, false);
        npDiscount = rootView.findViewById(R.id.npDiscount);

        npDiscount.setMinValue(0);
        npDiscount.setMaxValue(100);

        npDiscount.setWrapSelectorWheel(true);

        switchChangeFragment = rootView.findViewById(R.id.switchChangeFragment);

        loadFragment(new ExistingCategoriesFragment());

        switchChangeFragment.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                loadFragment(new NewCategoryFragment());
            } else {
                loadFragment(new ExistingCategoriesFragment());
            }
        });

        selectCard = rootView.findViewById(R.id.selectEType);
        tvETypes = rootView.findViewById(R.id.tvETypse);
        selectedETypes = new boolean[eTypesArray.length];

        selectCard.setOnClickListener(v -> {
            showETypesDialog();
        });


        return rootView;
    }
    private void showETypesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select event types");
        builder.setCancelable(false);
        builder.setMultiChoiceItems(eTypesArray, selectedETypes, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    eTypesList.add(which);
                }else{
                    eTypesList.remove(which);
                }
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView3, fragment)
                .commit();
    }
}