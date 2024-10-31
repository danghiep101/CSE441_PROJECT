package com.example.admincse441project.ui.ticketmanagement.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.databinding.ItemTicketRowBinding;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private List<Ticket> ticketList;
    private TicketAdapter.OnTicketClickListener listener;

    public TicketAdapter(List<Ticket> ticketList, TicketAdapter.OnTicketClickListener listener) {
        this.ticketList = ticketList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTicketRowBinding binding = ItemTicketRowBinding.inflate(inflater, parent, false);
        return new TicketAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.ViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.bind(ticket);
        holder.itemView.setOnClickListener(v -> listener.onTicketClick(ticket));

        TextView txtStatus = holder.itemView.findViewById(R.id.txt_ticket_status);

        if (txtStatus.getText().toString().equals("Booked") || txtStatus.getText().toString().equals("Used"))
            txtStatus.setTextColor(holder.itemView.getContext().getColor(R.color.yellow_theme));
        else if (txtStatus.getText().toString().equals("Expired"))
            txtStatus.setTextColor(holder.itemView.getContext().getColor(R.color.red_theme));
    }

    @Override
    public int getItemCount() {
        return ticketList != null ? ticketList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTicketRowBinding binding;

        public ViewHolder(ItemTicketRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Ticket ticket) {
            binding.txtTicketId.setText(String.valueOf(ticket.getId()));
            binding.txtTicketSeat.setText(String.valueOf(ticket.getShowtimeId()));
            binding.txtTicketStatus.setText(String.valueOf(ticket.getStatus()));
        }
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
        notifyDataSetChanged();
    }

    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }
}
