package com.example.eventplanner.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.eventplanner.R;
import com.example.eventplanner.model.homepage.ServiceProductHomeResponse;

import java.util.List;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.ProductViewHolder> {

    private List<ServiceProductHomeResponse> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ServiceProductHomeResponse product);
    }

    public HomeItemsAdapter(List<ServiceProductHomeResponse> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    public void updateData(List<ServiceProductHomeResponse> newProducts) {
        this.productList.clear();
        this.productList.addAll(newProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ServiceProductHomeResponse product = productList.get(position);
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.price.setText(String.format("%.2f EURO", product.getPrice()));
        holder.category.setText(product.getCategory());
        holder.type.setText(product.getType());
        String base64Image = product.getImage();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                holder.imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                holder.imageView.setImageResource(R.drawable.placeholder_image); // fallback
                Log.e("HomeItemsAdapter", "Failed to decode Base64 image", e);
            }
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder_image); // ako nema slike
        }

        Log.d("HomeItemsAdapter", "Loading image: " + product.getImage());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, price, category,type;
        ImageView imageView; // ako koristiš slike

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
            category = itemView.findViewById(R.id.product_category);
            type=itemView.findViewById(R.id.product_type);
            imageView = itemView.findViewById(R.id.product_image); // ako koristiš slike

        }
    }

}
