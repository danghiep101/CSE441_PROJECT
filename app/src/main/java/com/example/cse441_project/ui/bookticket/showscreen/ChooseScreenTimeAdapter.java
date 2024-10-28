package com.example.cse441_project.ui.bookticket.showscreen;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.data.model.showtime.ShowTime;

import com.example.cse441_project.databinding.ItemTimeAndScreenBinding;

public class ChooseScreenTimeAdapter extends ListAdapter<ShowTime, ChooseScreenTimeAdapter.ShowTimeViewHolder> {
    private final OnShowTimeClickListener onShowTimeClickListener;

    public interface OnShowTimeClickListener {
        void onShowTimeClick(ShowTime showTime);
    }

    protected ChooseScreenTimeAdapter(OnShowTimeClickListener onShowTimeClickListener) {
        super(DIFF_CALLBACK);
        this.onShowTimeClickListener = onShowTimeClickListener;
    }

    @NonNull
    @Override
    public ShowTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimeAndScreenBinding binding = ItemTimeAndScreenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShowTimeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTimeViewHolder holder, int position) {
        ShowTime showTime = getItem(position);
        holder.bind(showTime);
    }

    class ShowTimeViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimeAndScreenBinding binding;

        public ShowTimeViewHolder(ItemTimeAndScreenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShowTime showTime) {
            binding.txtPlayTime.setText(showTime.getStartTime() + "-" + showTime.getEndTime());
            binding.txtScreen.setText(showTime.getName());
            binding.getRoot().setOnClickListener(v -> onShowTimeClickListener.onShowTimeClick(showTime));
        }
    }

    private static final DiffUtil.ItemCallback<ShowTime> DIFF_CALLBACK = new DiffUtil.ItemCallback<ShowTime>() {
        @Override
        public boolean areItemsTheSame(@NonNull ShowTime oldItem, @NonNull ShowTime newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ShowTime oldItem, @NonNull ShowTime newItem) {
            return oldItem.equals(newItem);
        }
    };
}
