package com.example.cse441_project.ui.bookticket.custom_adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.bookticket.instance.DateAndTimeItem;

import java.util.List;

public class DateAndTimeAdapter extends RecyclerView.Adapter<DateAndTimeAdapter.DateAndTimeViewHolder> {
    private List<DateAndTimeItem> list;
    private int selectedPosition = 0; // Không có phần tử nào được chọn mặc định

    public DateAndTimeAdapter(List<DateAndTimeItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DateAndTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_and_time, parent, false);
        return new DateAndTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAndTimeViewHolder holder, int position) {
        DateAndTimeItem item = list.get(position);
        holder.dayOfWeek.setText(item.getDayOfWeek());
        holder.dayOfMonth.setText(item.getDayOfMonth());

        if (position == selectedPosition) {
            holder.bg.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#64788A")));
        } else {
            holder.bg.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DateAndTimeViewHolder extends RecyclerView.ViewHolder {
        private TextView dayOfWeek;
        private TextView dayOfMonth;
        private LinearLayout bg;

        public DateAndTimeViewHolder(@NonNull View itemView) {
            super(itemView);

            dayOfWeek = itemView.findViewById(R.id.txt_day_of_week);
            dayOfMonth = itemView.findViewById(R.id.txt_day_of_month);
            bg = itemView.findViewById(R.id.bg_date_and_time);
        }
    }
}
