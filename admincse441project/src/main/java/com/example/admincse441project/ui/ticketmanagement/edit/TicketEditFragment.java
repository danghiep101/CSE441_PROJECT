package com.example.admincse441project.ui.ticketmanagement.edit;
import static com.example.admincse441project.utils.FirebaseUtils.deleteTicket;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.ui.ticketmanagement.list.TicketListFragment;
import com.example.admincse441project.utils.FirebaseUtils;

public class TicketEditFragment extends Fragment {
    private View view;
    private ImageView imgDeleteTicket;
    private ImageView btnBack;
    private String ticketId;

    private EditText edtTicketId;
    private EditText edtShowtimeId;
    private EditText edtSeat;
    private EditText edtStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ticket_edit, container, false);

        imgDeleteTicket = view.findViewById(R.id.img_delete_ticket);
        btnBack = view.findViewById(R.id.btn_back_edit_ticket);
        edtTicketId = view.findViewById(R.id.edt_edit_ticket_id);
        edtShowtimeId = view.findViewById(R.id.edt_edit_showtime_id);
        edtSeat = view.findViewById(R.id.edt_edit_seat);
        edtStatus = view.findViewById(R.id.edt_edit_status);

        // Tắt các EditText - không cho người dùng thay đổi dữ liệu
        edtTicketId.setEnabled(false);
        edtShowtimeId.setEnabled(false);
        edtSeat.setEnabled(false);
        edtStatus.setEnabled(false);

        // Lấy ra dữ liệu được gửi từ bundle bên phía TicketListFragment
        ticketId = getArguments().getString("TICKET_ID");

        // Truy vấn lấy ra dữ liệu của ticket có id xác định
        FirebaseUtils.getTicketById(ticketId)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Ticket ticket = documentSnapshot.toObject(Ticket.class);

                        // Gán dữ liệu đã lấy được vào các trường dữ liệu
                        edtTicketId.setText(ticket.getId());
                        edtShowtimeId.setText(ticket.getShowtimeId());
                        edtSeat.setText(ticket.getSeat());
                        edtStatus.setText(ticket.getStatus());
                    } else {
                        Log.d("Ticket không tồn tại", "Ticket không tồn tại");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("Lỗi khi lấy ticket: ", "Lỗi khi lấy ticket: ");
                });

        // Hàm xử lý các nút ấn có trên Fragment
        onViewClickListeners();

        return view;
    }

    // Hàm xử lý các nút ấn có trên Fragment
    private void onViewClickListeners() {
        btnBack.setOnClickListener(v -> {
            Fragment newFragment = new TicketListFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        imgDeleteTicket.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirm delete")
                    .setMessage("Are you sure you want to delete this ticket?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        deleteTicket(ticketId);
                        Toast.makeText(requireContext(), "Ticket deleted", Toast.LENGTH_SHORT).show();

                        Fragment newFragment = new TicketListFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}