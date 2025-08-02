package com.example.eventplanner.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        String imageUrl = product.getImage();
        if (imageUrl != null) {
            imageUrl = imageUrl.replace("localhost", "192.168.8.101"); // ako si na emulatoru
            // ili: imageUrl = imageUrl.replace("localhost", "192.168.1.5"); // ako si na telefonu
        }

        Glide.with(holder.itemView.getContext())
                .load(imageUrl) // mora biti validan URL
                .placeholder(R.drawable.placeholder_image) // dok se učitava
                .error(R.drawable.placeholder_image)             // ako pukne
                .into(holder.imageView);

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
