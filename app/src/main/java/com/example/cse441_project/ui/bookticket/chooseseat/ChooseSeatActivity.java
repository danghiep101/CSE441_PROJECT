package com.example.cse441_project.ui.bookticket.chooseseat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.data.model.seat.Seat;
import com.example.cse441_project.data.model.ticket.Ticket;
import com.example.cse441_project.ui.bookticket.ChooseVoucherActivity;
import com.example.cse441_project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChooseSeatActivity extends Activity {
    private RecyclerView rcvListSeat;
    private TextView txtPrice;
    private TextView txtNumberSeats;
    private Button btnContinue;

    SeatAdapter adapter;
    private List<String> unavailableSeatList = new ArrayList<>();
    private String showtimeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);

        rcvListSeat = findViewById(R.id.rcv_list_seat);
        txtPrice = findViewById(R.id.txt_choose_seat_price);
        txtNumberSeats = findViewById(R.id.txt_choose_seat_number);
        btnContinue = findViewById(R.id.btn_choose_seat_continue);

        // Lấy dữ liệu bên Activity khác
        showtimeId = getIntent().getStringExtra("SHOWTIME_ID");

        // Tạo danh sách ghế
        List<Seat> list = new ArrayList<>();
        for (char letter = 'A'; letter <= 'F'; letter++) {
            for (int number = 0; number <= 6; number++) {
                list.add(new Seat(letter + String.valueOf(number)));
            }
        }

        // Query vé đã mua => biết được chỗ ngồi đã bị chiếm dụng
        FirebaseUtils.getTicketsByShowtimeAndSeat(showtimeId)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            String seat = document.getString("seat");
                            unavailableSeatList.add(seat);
                        }

                        // Xử lý RecyclerView
                        adapter = new SeatAdapter(list, unavailableSeatList, ChooseSeatActivity.this, txtPrice, txtNumberSeats);
                        rcvListSeat.setAdapter(adapter);
                    } else {
                        System.err.println("Error getting tickets: " + task.getException());
                    }
                }
            });

        rcvListSeat.setLayoutManager(new GridLayoutManager(this, 7));
        int spaceInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()
        );
        rcvListSeat.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.left = spaceInDp;
                outRect.right = spaceInDp;
                outRect.top = spaceInDp;
                outRect.bottom = spaceInDp;
            }
        });

        btnContinue.setOnClickListener(v -> continueProcess());
    }

    private void continueProcess() {
        List<String> choosedSeats = adapter.getSelectedSeatList();
        String totalPrice = adapter.getTotalPrice();

        if (choosedSeats.size() > 0) {
            Intent intent = new Intent(this, ChooseVoucherActivity.class);

            ArrayList<String> selectedSeatsList = new ArrayList<>(choosedSeats);
            intent.putStringArrayListExtra("SELECTED_SEATS_LIST", selectedSeatsList);
            intent.putExtra("TOTAL_PRICE", totalPrice);
            intent.putExtra("SHOWTIME_ID", showtimeId);

            startActivity(intent);
            setResult(RESULT_OK, intent);
        }
    }
}