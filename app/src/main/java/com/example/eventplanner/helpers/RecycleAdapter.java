package com.example.eventplanner.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventplanner.R;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private ArrayList<Uri> uriArrayList;
    private ArrayList<Bitmap> bitmapList;
    private Context context;
    CountOfImageWhenRemoved countOfImageWhenRemoved;

    public RecycleAdapter(ArrayList<Uri> uriArrayList, ArrayList<Bitmap> bitmapList, Context context, CountOfImageWhenRemoved countOfImageWhenRemoved) {
        this.uriArrayList = uriArrayList;
        this.bitmapList = bitmapList;
        this.context = context;
        this.countOfImageWhenRemoved = countOfImageWhenRemoved;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_single_image, parent, false);

        return new ViewHolder(view, countOfImageWhenRemoved);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        if (position < bitmapList.size()) {
            holder.imageView.setImageBitmap(bitmapList.get(position));
        } else {
            int uriPosition = position - bitmapList.size();
            Glide.with(context)
                    .load(uriArrayList.get(uriPosition))
                    .into(holder.imageView);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < bitmapList.size()) {
                    Bitmap removedBitmap = bitmapList.get(position);
                    bitmapList.remove(position);
                    countOfImageWhenRemoved.onBackendImageRemoved(removedBitmap);
                } else {
                    int uriPosition = position - bitmapList.size();
                    Uri removedUri = uriArrayList.get(uriPosition);
                    uriArrayList.remove(uriPosition);
                    countOfImageWhenRemoved.onUriImageRemoved(removedUri);
                }
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                countOfImageWhenRemoved.clicked(getItemCount());
            }
        });

    }

    @Override
    public int getItemCount() {
        return uriArrayList.size() + bitmapList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, delete;
        CountOfImageWhenRemoved countOfImageWhenRemoved;

        public ViewHolder(@NonNull View itemView, CountOfImageWhenRemoved countOfImageWhenRemoved) {
            super(itemView);
            this.countOfImageWhenRemoved = countOfImageWhenRemoved;
            imageView = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public interface CountOfImageWhenRemoved {
        void clicked(int getSize);
        void onBackendImageRemoved(Bitmap bitmap);
        void onUriImageRemoved(Uri uri);
    }
}
