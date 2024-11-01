
package com.example.cse441_project.ui.bookticket.chooseseat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.data.model.seat.Seat;
import com.example.cse441_project.data.model.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {
    private List<Seat> list;
    private List<String> unavailableSeatList;
    private List<String> selectedSeatList;
    private List<Ticket> listTickets;
    private Context context;
    private String totalPrice;

    private TextView txtPrice;
    private TextView txtNumberSeats;

    public SeatAdapter(List<Seat> list, List<String> unavailableSeatList, Context context, TextView txtPrice, TextView txtNumberSeats, List<Ticket> listTickets) {
        this.list = list;
        this.unavailableSeatList = unavailableSeatList;
        this.selectedSeatList = new ArrayList<>();
        this.context = context;
        this.txtPrice = txtPrice;
        this.txtNumberSeats = txtNumberSeats;
        this.listTickets = listTickets;
    }

    public List<String> getSelectedSeatList() {
        return selectedSeatList;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Seat seat = list.get(position);
        holder.name.setText(seat.getName());

        // Thực hiện thay đổi màu của các seat item theo điều kiện
        if (unavailableSeatList.contains(seat.getName())) {
            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundColor(Color.argb(50, 200, 44, 50));
        } else if (selectedSeatList.contains(seat.getName())) {
            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundColor(Color.argb(50 ,10 ,156, 161));
        } else {
            holder.name.setTextColor(holder.itemView.getContext().getColor(R.color.button_gradiant_2));
            holder.name.setBackgroundColor(Color.argb(50, 152, 120, 81));
        }

        // Thực hiện hành động ấn vào các seat item
        holder.itemView.setOnClickListener(view -> {
            // Kiểm tra nếu listTickets có kích thước bằng 0 hoặc số lượng ghế được chọn lớn hơn số lượng vé hiện có thì sẽ không cho chọn ghế nữa
            if (listTickets.size() == 0 || selectedSeatList.size() >= listTickets.size()) {
                Toast.makeText(context, "Cannot select seats because no tickets are available", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra nếu chọn vào ghế đã mua thì không cho chọn.
            if (unavailableSeatList.contains(seat.getName())) {
                Toast.makeText(context, "This seat is already occupied", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                if (!selectedSeatList.contains(seat.getName())) {
                    selectedSeatList.add(seat.getName());
                } else {
                    selectedSeatList.remove(seat.getName());
                }

                notifyItemChanged(position);
            }

            totalPrice = (selectedSeatList.size() * 80000) + "";
            txtPrice.setText(totalPrice + " vnd");
            String numberSeats = selectedSeatList.size() <= 1 ? " seat" : " seats";
            txtNumberSeats.setText(selectedSeatList.size() + numberSeats);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_item_seat);
        }
    }
}