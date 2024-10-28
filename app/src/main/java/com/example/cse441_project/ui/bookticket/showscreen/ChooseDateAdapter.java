package com.example.cse441_project.ui.bookticket.showscreen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.data.model.showtime.ShowTime;
import com.example.cse441_project.databinding.ItemDateAndTimeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChooseDateAdapter extends RecyclerView.Adapter<ChooseDateAdapter.ViewHolder> {
    private List<ShowTime> showTimeList = new ArrayList<>();
    private final OnDateClickListener onDateClickListener;

    public ChooseDateAdapter(OnDateClickListener listener) {
        this.onDateClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDateAndTimeBinding binding = ItemDateAndTimeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowTime showTime = showTimeList.get(position);
        holder.bind(showTime);
    }

    @Override
    public int getItemCount() {
        return showTimeList.size();
    }

    public void submitList(List<ShowTime> newList) {
        showTimeList.clear();
        showTimeList.addAll(newList);
        sortShowTimesByDay();
        Log.d("ChooseDateAdapter", "Dữ liệu trong submitList: " + newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDateAndTimeBinding binding;

        public ViewHolder(@NonNull ItemDateAndTimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                String date = binding.txtDayOfMonth.getText().toString();
                onDateClickListener.onDateClick(date);
            });
        }

        public void bind(ShowTime showTime) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            try {
                Date date = format.parse(showTime.getDate());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                String[] daysOfWeek = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
                String dayName = daysOfWeek[dayOfWeek - 1];

                binding.txtDayOfMonth.setText(showTime.getDate());
                binding.txtDayOfWeek.setText(dayName);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
    public void sortShowTimesByDay() {
        showTimeList.sort(new Comparator<ShowTime>() {
            @Override
            public int compare(ShowTime o1, ShowTime o2) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                try {
                    Date date1 = format.parse(o1.getDate());
                    Date date2 = format.parse(o2.getDate());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(date1);
                    int dayOfWeek1 = calendar1.get(Calendar.DAY_OF_WEEK);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(date2);
                    int dayOfWeek2 = calendar2.get(Calendar.DAY_OF_WEEK);

                    return Integer.compare(dayOfWeek1 == 1 ? 7 : dayOfWeek1 - 1, dayOfWeek2 == 1 ? 7 : dayOfWeek2 - 1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }
    public interface OnDateClickListener {
        void onDateClick(String date);
    }
}
