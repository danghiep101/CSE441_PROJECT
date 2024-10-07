package com.example.cse441_project.ui.bookticket.custom_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.bookticket.instance.PopcornAndDrinkItem;

import java.util.List;

public class PopcornAndDrinkAdapter extends RecyclerView.Adapter<PopcornAndDrinkAdapter.PopcornAndDrinkViewHolder> {
    private List<PopcornAndDrinkItem> list;

    public PopcornAndDrinkAdapter(List<PopcornAndDrinkItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PopcornAndDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popcorn_and_drink, parent, false);
        return new PopcornAndDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopcornAndDrinkViewHolder holder, int position) {
        PopcornAndDrinkItem item = list.get(position);
        holder.image.setImageResource(item.getImage());
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PopcornAndDrinkViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView price;

        public PopcornAndDrinkViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img_popcorn_and_drink);
            name = itemView.findViewById(R.id.txt_popcorn_and_drink_name);
            price = itemView.findViewById(R.id.txt_popcorn_and_drink_price);
        }
    }
}