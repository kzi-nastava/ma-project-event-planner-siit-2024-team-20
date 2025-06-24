package com.example.eventplanner.helpers;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private final List<Object> allImages = new ArrayList<>();
    private final List<Uri> selectedUris = new ArrayList<>();

    public GalleryAdapter(List<Uri> initialUris) {
        if (initialUris != null) {
            allImages.addAll(initialUris);
        }
    }

    public void addUriImage(Uri uri) {
        allImages.add(uri);
        notifyDataSetChanged();
    }

    public void addBitmapImages(List<Bitmap> bitmaps) {
        allImages.clear();
        allImages.addAll(bitmaps);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = allImages.get(position);

        if (item instanceof Uri) {
            Uri imageUri = (Uri) item;
            holder.imageView.setImageURI(imageUri);

            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setOnCheckedChangeListener(null);

            holder.checkBox.setChecked(selectedUris.contains(imageUri));
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedUris.add(imageUri);
                } else {
                    selectedUris.remove(imageUri);
                }
            });

        } else if (item instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) item;
            holder.imageView.setImageBitmap(bitmap);

            //holder.checkBox.setVisibility(View.GONE);
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return allImages.size();
    }

    public List<Uri> getSelectedUris() {
        return new ArrayList<>(selectedUris);
    }

    public void removeSelectedUris() {
        allImages.removeAll(selectedUris);
        selectedUris.clear();
        notifyDataSetChanged();
    }

    public void clearAllImages() {
        allImages.clear();
        selectedUris.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}