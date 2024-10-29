package com.example.cse441_project.ui.bookticket.showscreen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.data.model.showtime.ShowTime;
import com.example.cse441_project.databinding.ItemTimeAndScreenBinding;

import java.util.ArrayList;
import java.util.List;

public class ChooseScreenTimeAdapter extends RecyclerView.Adapter<ChooseScreenTimeAdapter.ShowTimeViewHolder> {
    private List<ShowTime> showTimeList = new ArrayList<>();
    private final OnShowTimeClickListener onShowTimeClickListener;

    public interface OnShowTimeClickListener {
        void onShowTimeClick(ShowTime showTime);
    }

    public ChooseScreenTimeAdapter(OnShowTimeClickListener onShowTimeClickListener) {
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
        ShowTime showTime = showTimeList.get(position);
        holder.bind(showTime);
    }

    @Override
    public int getItemCount() {
        return showTimeList.size();
    }

    public void submitList(List<ShowTime> newList) {
        this.showTimeList = newList;
        Log.d("ChooseScreenTimeAdapter", "Dữ liệu trong submitList: " + newList);
        notifyDataSetChanged();
    }

    class ShowTimeViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimeAndScreenBinding binding;

        public ShowTimeViewHolder(ItemTimeAndScreenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShowTime showTime) {;
            binding.txtPlayTime.setText(showTime.getStartTime() + " - " + showTime.getEndTime());
            binding.txtScreen.setText(showTime.getNameCinema());
            binding.getRoot().setOnClickListener(v -> onShowTimeClickListener.onShowTimeClick(showTime));
        }
    }
}
