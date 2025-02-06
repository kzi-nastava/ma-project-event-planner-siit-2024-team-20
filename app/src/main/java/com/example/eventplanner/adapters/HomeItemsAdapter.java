package com.example.eventplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.model.entities.BaseItem;
import com.example.eventplanner.R;
import java.util.List;

public class HomeItemsAdapter extends RecyclerView.Adapter<HomeItemsAdapter.ItemViewHolder> {

    private List<BaseItem> itemsList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BaseItem item);
    }

    public HomeItemsAdapter(List<BaseItem> itemsList, OnItemClickListener listener) {
        this.itemsList = itemsList;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_product_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        BaseItem item = itemsList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText(String.valueOf(item.getPrice()));
        holder.category.setText(String.valueOf(item.getCategory()));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, price,category;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.product_price);
            category=itemView.findViewById(R.id.product_category);
        }
    }
}
