package com.example.cse441_project.ui.bookticket.showscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.databinding.ItemDateAndTimeBinding;


public class ChooseDateAdapter extends ListAdapter<String, ChooseDateAdapter.DateViewHolder> {
    private final OnDateClickListener onDateClickListener;

    public interface OnDateClickListener {
        void onDateClick(String date);
    }

    protected ChooseDateAdapter(OnDateClickListener onDateClickListener) {
        super(DIFF_CALLBACK);
        this.onDateClickListener = onDateClickListener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDateAndTimeBinding binding = ItemDateAndTimeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String date = getItem(position);
        holder.bind(date);
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private final ItemDateAndTimeBinding binding;

        public DateViewHolder(ItemDateAndTimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String date) {
            binding.txtDayOfMonth.setText(date);
            binding.getRoot().setOnClickListener(v -> onDateClickListener.onDateClick(date));
        }
    }

    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };
}
