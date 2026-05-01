package com.example.lostfoundapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(LostFoundItem item);
    }

    private Context context;
    private ArrayList<LostFoundItem> items;
    private OnItemClickListener listener;

    public ItemAdapter(Context context, ArrayList<LostFoundItem> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lost_found, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        LostFoundItem item = items.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String postedTime = sdf.format(new Date(item.getTimestamp()));

        holder.titleTextView.setText(item.getType() + ": " + item.getName());
        holder.categoryTextView.setText("Category: " + item.getCategory());
        holder.dateTextView.setText("Posted: " + postedTime);
        holder.locationTextView.setText("Location: " + item.getLocation());

        try {
            holder.itemImageView.setImageURI(Uri.parse(item.getImageUri()));
        } catch (Exception e) {
            holder.itemImageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView titleTextView, categoryTextView, dateTextView, locationTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}