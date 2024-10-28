package com.example.admincse441project.ui.showtimemaganement;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.admincse441project.data.model.showtime.ShowTime;
import com.example.admincse441project.databinding.ItemShowtimeBinding;
import java.util.List;

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ViewHolder> {
    private List<ShowTime> showTimeList;
    private OnShowTimeClickListener listener;

    public ShowTimeAdapter(List<ShowTime> showTimeList, OnShowTimeClickListener listener) {
        this.showTimeList = showTimeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemShowtimeBinding binding = ItemShowtimeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTimeAdapter.ViewHolder holder, int position) {
        ShowTime showtime = showTimeList.get(position);

        // Set STT (Số thứ tự)
        holder.binding.txtTicketNumber.setText(String.valueOf(position + 1));

        // Bind dữ liệu showtime
        holder.bind(showtime);
        holder.itemView.setOnClickListener(v -> listener.onShowTimeClick(showtime));
    }

    @Override
    public int getItemCount() {
        return showTimeList != null ? showTimeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemShowtimeBinding binding;

        public ViewHolder(ItemShowtimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShowTime showTime) {
            binding.textView23.setText(String.valueOf(showTime.getName()));
            binding.textView33.setText(String.valueOf(showTime.getNameCinema()));
            binding.textView14.setText(String.valueOf(showTime.getStartTime() + " - " + showTime.getEndTime()));
            binding.txtDate.setText(String.valueOf(showTime.getDate()));
        }
    }

    public void setShowTimeList(List<ShowTime> showTimeList) {
        this.showTimeList = showTimeList;
        notifyDataSetChanged();
    }

    public interface OnShowTimeClickListener {
        void onShowTimeClick(ShowTime showtime);
    }
}
