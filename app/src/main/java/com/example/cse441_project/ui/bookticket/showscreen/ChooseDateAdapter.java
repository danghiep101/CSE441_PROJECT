package com.example.cse441_project.ui.bookticket.showscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.data.showscreen.ShowScreen;
import com.example.cse441_project.databinding.ItemDateAndTimeBinding;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChooseDateAdapter extends RecyclerView.Adapter<ChooseDateAdapter.ViewHolder>{
    private List<ShowScreen> showScreenList = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    public void setShowScreenList(List<ShowScreen> screens) {
        this.showScreenList = screens;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChooseDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDateAndTimeBinding binding = ItemDateAndTimeBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseDateAdapter.ViewHolder holder, int position) {
        ShowScreen screen = showScreenList.get(position);
        holder.bind(screen);
    }

    @Override
    public int getItemCount() {
        return showScreenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDateAndTimeBinding binding;

        public ViewHolder(ItemDateAndTimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShowScreen screen) {

            String date = String.valueOf(screen.getDate());
            binding.txtDayOfWeek.setText(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(screen.getDate());
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String dayOfWeekString = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
            binding.txtDayOfWeek.setText(dayOfWeekString);
            binding.txtDayOfMonth.setText(date);
        }
    }
}
