package com.example.eventplanner.fragments.event;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.eventplanner.R;
import com.example.eventplanner.model.eventDetails.EventStatResponse;
import com.example.eventplanner.services.IEventService;
import com.example.eventplanner.services.spec.ApiService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdditionalInformationFragment extends Fragment {

    private PieChart pieChart;
    private PieChart pieChartLikes;
    private ProgressBar likedProgress;
    private TextView likedPercentage;
    private Long eventId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getLong("event_id", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_additional_information, container, false);

        pieChart = rootView.findViewById(R.id.pie_chart);
        pieChartLikes = rootView.findViewById(R.id.pie_chart_likes);
        likedProgress = rootView.findViewById(R.id.liked_progress);
        likedPercentage = rootView.findViewById(R.id.liked_percentage);
        LinearLayout downloadButton = rootView.findViewById(R.id.pdf_download_button);
        downloadButton.setOnClickListener(v -> generatePdf());

        if (eventId == null || eventId == -1) return rootView;

        IEventService service = ApiService.getEventService();
        service.getStat(eventId).enqueue(new Callback<EventStatResponse>() {
            @Override
            public void onResponse(Call<EventStatResponse> call, Response<EventStatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EventStatResponse data = response.body();
                    setupPieChartGrades(data);
                    setupPieChartLikes(data);
                    updateProgressBars(data);
                }
            }

            @Override
            public void onFailure(Call<EventStatResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return rootView;
    }

    private void setupPieChartGrades(EventStatResponse data) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        List<Integer> values = List.of(data.getOne(), data.getTwo(), data.getThree(), data.getFour(), data.getFive());
        boolean isAllZero = values.stream().allMatch(v -> v == 0);

        if (isAllZero) {
            entries.add(new PieEntry(1, "No Data"));
        } else {
            if (data.getOne() > 0) entries.add(new PieEntry(data.getOne(), "Grade 1"));
            if (data.getTwo() > 0) entries.add(new PieEntry(data.getTwo(), "Grade 2"));
            if (data.getThree() > 0) entries.add(new PieEntry(data.getThree(), "Grade 3"));
            if (data.getFour() > 0) entries.add(new PieEntry(data.getFour(), "Grade 4"));
            if (data.getFive() > 0) entries.add(new PieEntry(data.getFive(), "Grade 5"));
        }


        PieDataSet dataSet = new PieDataSet(entries, "");
        if (isAllZero) {
            dataSet.setColors(Color.LTGRAY);
            dataSet.setValueTextColor(Color.GRAY);
        } else {
            dataSet.setColors(
                    Color.argb(102, 0, 128, 128),
                    Color.argb(102, 220, 20, 60),
                    Color.argb(102, 255, 165, 0),
                    Color.argb(102, 75, 0, 130),
                    Color.argb(102, 34, 139, 34)
            );
            dataSet.setValueTextColor(Color.WHITE);
        }

        dataSet.setValueTextSize(12f);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pieChart.getLegend().setDrawInside(false);
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();
    }
    private void setupPieChartLikes(EventStatResponse data) {
        int liked = data.getLiked();
        int total = data.getUsers();
        int notLiked = total - liked;

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(liked, "Liked"));
        entries.add(new PieEntry(notLiked, "Not Liked"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(
                Color.rgb(255, 105, 180),
                Color.rgb(200, 200, 200)
        );
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getPieLabel(float value, PieEntry entry) {
                if ("Liked".equals(entry.getLabel()) && value > 0) {
                    return String.valueOf((int) value);
                }
                return "";
            }
        });

        PieData pieData = new PieData(dataSet);
        pieChartLikes.setData(pieData);
        pieChartLikes.getDescription().setEnabled(false);

        Legend legend = pieChartLikes.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        pieChartLikes.setUsePercentValues(false);
        pieChartLikes.animateXY(1000, 1000);
        pieChartLikes.invalidate();
    }



    private void updateProgressBars(EventStatResponse data) {
        int total = data.getUsers();
        if (total <= 0) return;

        int liked = data.getLiked();
        int accepted = data.getAccepted();

        int likedPercent = Math.round((liked * 100f) / total);
        likedProgress.setProgress(likedPercent);
        likedPercentage.setText(likedPercent + "%");

    }
    private void generatePdf() {
        Bitmap gradesBitmap = pieChart.getChartBitmap();
        Bitmap likesBitmap = pieChartLikes.getChartBitmap();

        PdfDocument document = new PdfDocument();
        Paint paint = new Paint();

        // Page 1 - Grades Chart
        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(595, 842, 1).create(); // A4
        PdfDocument.Page page1 = document.startPage(pageInfo1);
        Canvas canvas1 = page1.getCanvas();

        int x = 50, y = 50;

        paint.setTextSize(20f);
        paint.setFakeBoldText(true);
        canvas1.drawText("Event Statistics", x, y, paint);

        y += 40;

        paint.setTextSize(16f);
        paint.setFakeBoldText(false);

        if (gradesBitmap != null) {
            canvas1.drawText("Grades", x, y, paint);
            y += 20;
            canvas1.drawBitmap(Bitmap.createScaledBitmap(gradesBitmap, 400, 400, true), x, y, paint);
        }


        document.finishPage(page1);

        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(595, 842, 2).create(); // A4
        PdfDocument.Page page2 = document.startPage(pageInfo2);
        Canvas canvas2 = page2.getCanvas();

        x = 50;
        y = 50;
        if (likesBitmap != null) {
            paint.setTextSize(16f);
            canvas2.drawText("Liked ", x, y, paint);
            y += 20;
            canvas2.drawBitmap(Bitmap.createScaledBitmap(likesBitmap, 400, 400, true), x, y, paint);
        }

        document.finishPage(page2);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "event_stats.pdf");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            ContentResolver resolver = requireContext().getContentResolver();
            Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

            try {
                if (uri != null) {
                    OutputStream outputStream = resolver.openOutputStream(uri);
                    document.writeTo(outputStream);
                    document.close();
                    outputStream.close();
                    Toast.makeText(getContext(), "PDF saved to Downloads", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to save PDF", Toast.LENGTH_SHORT).show();
            }
        } else {
            String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
            String filePath = directoryPath + "event_stats.pdf";

            try {
                File file = new File(filePath);
                FileOutputStream fos = new FileOutputStream(file);
                document.writeTo(fos);
                document.close();
                fos.close();
                Toast.makeText(getContext(), "PDF saved: " + filePath, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to save PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
