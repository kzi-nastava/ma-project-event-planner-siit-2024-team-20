package com.example.eventplanner.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.model.eventPage.AgendaResponse;
import com.example.eventplanner.model.eventPage.EventDisplayResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventPdfGenerator {
    private final Context context;

    public EventPdfGenerator(Context context) {
        this.context = context;
    }

    public void generatePdf(EventDisplayResponse event) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        int y = 30;

        paint.setTextSize(20);
        paint.setFakeBoldText(true);
        paint.setColor(context.getResources().getColor(R.color.text_color));
        canvas.drawText("Event Details: " + event.getName(), 40, y, paint);

        paint.setTextSize(14);
        paint.setFakeBoldText(false);
        paint.setColor(context.getResources().getColor(R.color.black));
        y += 40;

        canvas.drawText("Description:", 40, y, paint);
        y += 20;

        String description = event.getDescription();
        int maxLineWidth = 515;
        y = drawMultilineText(description, canvas, paint, y, 40, maxLineWidth);

        y += 20;
        canvas.drawLine(40, y, 555, y, paint);
        y += 20;

        canvas.drawText("Date: " + event.getStartDate() + " to " + event.getEndDate(), 40, y, paint);
        y += 20;
        canvas.drawText("Time: " + event.getStartTime() + " to " + event.getEndTime(), 40, y, paint);
        y += 20;
        canvas.drawText("Organizer: " + event.getOrganizer().getEmail(), 40, y, paint);
        y += 20;
        canvas.drawText("Location: " + event.getAddress(), 40, y, paint);
        y += 20;
        canvas.drawText("Private: " + (event.isPrivate() ? "Yes" : "No"), 40, y, paint);
        y += 20;
        canvas.drawText("Guests: " + event.getGuests(), 40, y, paint);
        y += 30;

        List<AgendaResponse> agenda = event.getAgenda();
        if (agenda == null || agenda.isEmpty()) {
            canvas.drawLine(40, y, 555, y, paint);
        } else {
            canvas.drawText("Agenda:", 40, y, paint);
            y += 20;
            for (AgendaResponse item : agenda) {
                if (y > 800) {
                    document.finishPage(page);
                    pageInfo = new PdfDocument.PageInfo.Builder(595, 842, document.getPages().size() + 1).create();
                    page = document.startPage(pageInfo);
                    canvas = page.getCanvas();
                    y = 30;
                }
                paint.setFakeBoldText(true);
                canvas.drawText("Name: " + item.getName(), 40, y, paint);
                paint.setFakeBoldText(false);
                y += 20;

                String agendaDescription = "Description: " + item.getDescription();
                y = drawMultilineText(agendaDescription, canvas, paint, y, 40, maxLineWidth);
                y += 10;

                canvas.drawText("Time: (" + formatTime(item.getFromTime()) + " to " + formatTime(item.getToTime()) + ")", 40, y, paint);
                y += 20;
                canvas.drawText("At: " + item.getLocation(), 40, y, paint);
                y += 30;
            }
        }

        document.finishPage(page);

        String directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File file = new File(directory_path, "event-info.pdf");

        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "PDF saved to Downloads folder", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        document.close();
    }

    private int drawMultilineText(String text, Canvas canvas, Paint paint, int y, int x, int maxWidth) {
        int lineHeight = (int)(paint.getTextSize() * 1.2);
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            float testWidth = paint.measureText(testLine);
            if (testWidth > maxWidth) {
                canvas.drawText(line.toString(), x, y, paint);
                y += lineHeight;
                line = new StringBuilder(word);
            } else {
                if (line.length() > 0) line.append(" ");
                line.append(word);
            }
        }
        if (line.length() > 0) {
            canvas.drawText(line.toString(), x, y, paint);
            y += lineHeight;
        }
        return y;
    }

    private String formatTime(LocalTime time) {
        if (time == null) return "N/A";
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
