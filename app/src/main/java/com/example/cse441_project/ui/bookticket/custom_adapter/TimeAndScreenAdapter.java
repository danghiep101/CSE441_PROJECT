package com.example.cse441_project.ui.bookticket.custom_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.bookticket.instance.TimeAndScreen;

import java.util.List;

public class TimeAndScreenAdapter extends RecyclerView.Adapter<TimeAndScreenAdapter.TimeAndScreenViewHolder> {
    private List<TimeAndScreen> list;

    public TimeAndScreenAdapter(List<TimeAndScreen> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TimeAndScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_and_screen, parent, false);
        return new TimeAndScreenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAndScreenViewHolder holder, int position) {
        TimeAndScreen item = list.get(position);
        holder.screen.setText(item.getScreen());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TimeAndScreenViewHolder extends  RecyclerView.ViewHolder {
        private TextView screen;
        private TextView time;

        public TimeAndScreenViewHolder(@NonNull View itemView) {
            super(itemView);

            screen = itemView.findViewById(R.id.txt_screen);
            time = itemView.findViewById(R.id.txt_play_time);
        }
    }
}
