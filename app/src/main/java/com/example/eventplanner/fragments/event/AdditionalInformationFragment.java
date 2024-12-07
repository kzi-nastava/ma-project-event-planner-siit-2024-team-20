package com.example.eventplanner.fragments.event;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventplanner.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdditionalInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdditionalInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    PieChart pieChart;
    PieChart pieChartAttendance;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdditionalInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdditionalInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdditionalInformationFragment newInstance(String param1, String param2) {
        AdditionalInformationFragment fragment = new AdditionalInformationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_additional_information, container, false);


        pieChart = rootView.findViewById(R.id.pie_chart);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        List<Map.Entry<Integer, Integer>> grades = new ArrayList<>();
        grades.add(new AbstractMap.SimpleEntry<>(1, 3));
        grades.add(new AbstractMap.SimpleEntry<>(2, 4));
        grades.add(new AbstractMap.SimpleEntry<>(3, 1));
        grades.add(new AbstractMap.SimpleEntry<>(4, 5));
        grades.add(new AbstractMap.SimpleEntry<>(5, 2));

        for (Map.Entry<Integer, Integer> pair : grades) {
            PieEntry pieEntry = new PieEntry(pair.getValue(),"Grade " + pair.getKey());
            pieEntries.add(pieEntry);
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Grades");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setDrawValues(true);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setValueTextColor(Color.WHITE);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(5000, 5000);
        pieChart.getDescription().setEnabled(false);

        Legend pieLegend = pieChart.getLegend();
        pieLegend.setEnabled(true);
        pieLegend.setTextColor(Color.parseColor("#3C2956"));
        pieLegend.setTextSize(12f);
        pieLegend.setForm(Legend.LegendForm.CIRCLE);


        //drugi pie chart
        pieChartAttendance = rootView.findViewById(R.id.pie_chart_attendance);
        int totalSeats = 160;
        int attended = 73;
        int notAttended = totalSeats - attended;

        ArrayList<PieEntry> pieEntriesAttendance = new ArrayList<>();
        pieEntriesAttendance.add(new PieEntry(attended, "attended"));
        pieEntriesAttendance.add(new PieEntry(notAttended, "not"));

        PieDataSet pieDataSetAttendance = new PieDataSet(pieEntriesAttendance, "");
        pieDataSetAttendance.setColors(ColorTemplate.PASTEL_COLORS);
        PieData pieDataAttendance = new PieData(pieDataSetAttendance);
        pieChartAttendance.setData(pieDataAttendance);
        pieChartAttendance.getDescription().setEnabled(false);
        pieChartAttendance.animateXY(5000, 5000);
        pieChartAttendance.invalidate();

        return rootView;
    }
}