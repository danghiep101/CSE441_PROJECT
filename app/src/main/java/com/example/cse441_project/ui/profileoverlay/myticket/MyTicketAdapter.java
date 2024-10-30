package com.example.cse441_project.ui.profileoverlay.myticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.data.model.seat.Seat;
import com.example.cse441_project.data.model.ticket.Ticket;
import com.example.cse441_project.ui.bookticket.chooseseat.SeatAdapter;
import com.example.cse441_project.utils.FirebaseUtils;

import java.util.List;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketAdapter.ViewHolder> {
    private List<Ticket> list;

    public MyTicketAdapter(List<Ticket> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_ticket, parent, false);
        return new MyTicketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = list.get(position);

        FirebaseUtils.getShowtimeById(ticket.getShowtimeId()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String screen = task.getResult().getString("nameCinema");
                        String movie = task.getResult().getString("name");
                        String date = task.getResult().getString("date");
                        String start = task.getResult().getString("startTime");
                        String end = task.getResult().getString("endTime");

                        holder.txtScreen.setText(screen != null ? screen : "N/A");
                        holder.txtMovie.setText(movie != null ? movie : "N/A");
                        holder.txtTime.setText(date + " " + start + " - " + end);
                        holder.txtSeat.setText(ticket.getSeat());
                    } else {
                        holder.txtMovie.setText("Showtime not found");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtScreen;
        private TextView txtSeat;
        private TextView txtMovie;
        private TextView txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtMovie = itemView.findViewById(R.id.txt_my_ticket_movie);
            this.txtScreen = itemView.findViewById(R.id.txt_my_ticket_screen);
            this.txtSeat = itemView.findViewById(R.id.txt_my_ticket_seat);
            this.txtTime = itemView.findViewById(R.id.txt_my_ticket_time);
        }
    }
}