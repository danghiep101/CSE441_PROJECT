package com.example.cse441_project.ui.bookticket.showscreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.data.showscreen.ShowScreen;
import com.example.cse441_project.databinding.ItemTimeAndScreenBinding;

import java.util.ArrayList;
import java.util.List;

public class ChooseScreenTimeAdapter extends RecyclerView.Adapter<ChooseScreenTimeAdapter.ViewHolder> {
    private List<ShowScreen> showScreenList = new ArrayList<>();

    public void setShowScreenList(List<ShowScreen> screens) {
        this.showScreenList = screens;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimeAndScreenBinding binding = ItemTimeAndScreenBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowScreen screen = showScreenList.get(position);
        holder.bind(screen);
    }

    @Override
    public int getItemCount() {
        return showScreenList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimeAndScreenBinding binding;

        public ViewHolder(ItemTimeAndScreenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShowScreen screen) {
            binding.txtPlayTime.setText(screen.getName());
            binding.txtScreen.setText(screen.getStartTime().toString() + " - " + screen.getEndTime().toString());
        }
    }
}
