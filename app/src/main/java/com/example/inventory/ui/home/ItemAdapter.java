package com.example.inventory.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventory.R;
import com.example.inventory.data.Item;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private List<Item> itemList = new ArrayList<>();
    private final OnItemClickListener listener;

    public ItemAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setItemList(List<Item> items) {
        this.itemList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = itemList.get(position);
        holder.bind(currentItem, listener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final TextView textViewPrice;
        private final TextView textViewQuantity;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_item_name);
            textViewPrice = itemView.findViewById(R.id.text_view_item_price);
            textViewQuantity = itemView.findViewById(R.id.text_view_item_quantity);
        }

        public void bind(Item item, OnItemClickListener listener) {
            textViewName.setText(item.getName());
            textViewPrice.setText(formatPrice(item.getPrice()));
            textViewQuantity.setText(itemView.getContext().getString(R.string.in_stock, item.getQuantity()));

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }

        private String formatPrice(double price) {
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
            return format.format(price);
        }
    }
}
