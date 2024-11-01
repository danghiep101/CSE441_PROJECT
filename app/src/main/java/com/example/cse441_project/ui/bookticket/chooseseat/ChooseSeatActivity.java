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

import com.example.cse441_project.ui.bookticket.PaymentActivity;
import com.example.cse441_project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChooseSeatActivity extends Activity {
    private RecyclerView rcvListSeat;
    private TextView txtPrice;
    private TextView txtNumberSeats;
    private Button btnContinue;
    private TextView txtNameMovie;
    private TextView txtTime;


    SeatAdapter adapter;
    private List<String> unavailableSeatList = new ArrayList<>();
    private List<Ticket> listTickets = new ArrayList<>();
    private String showtimeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);

        rcvListSeat = findViewById(R.id.rcv_list_seat);
        txtPrice = findViewById(R.id.txt_choose_seat_price);
        txtNumberSeats = findViewById(R.id.txt_choose_seat_number);
        btnContinue = findViewById(R.id.btn_choose_seat_continue);
        txtNameMovie = findViewById(R.id.txt_choose_seat_movie_name);
        txtTime = findViewById(R.id.txt_choose_seat_time);

        // Lấy ra dữ liệu được gửi qua intent từ ShowtimeActivity và đưa dữ liệu đó vào txtNameMovie và txtTime
        txtNameMovie.setText(getIntent().getStringExtra("SHOWTIME_MOVIE"));
        txtTime.setText(getIntent().getStringExtra("SHOWTIME_START") + " - " + getIntent().getStringExtra("SHOWTIME_END"));

        // Lấy ra showtimeId được gửi qua intent từ ShowtimeActivity
        showtimeId = getIntent().getStringExtra("SHOWTIME_ID");

        // Tạo danh sách ghế
        List<Seat> list = new ArrayList<>();
        for (char letter = 'A'; letter <= 'F'; letter++) {
            for (int number = 0; number <= 6; number++) {
                list.add(new Seat(letter + String.valueOf(number)));
            }
        }

        // Query vé đã mua => biết được chỗ ngồi đã bị chiếm dụng
        // Mỗi khi 1 trong các vé có showtimeId == showtimeId được chuyền vào cho getTicketsByShowtimeAndSeat. Khi dữ liệu của ticket đó thay đổi sẽ chạy lại đoạn code trong addSnapshotListener
        FirebaseUtils.getTicketsByShowtimeAndSeat(showtimeId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            System.err.println("Error getting tickets: " + e);
                            return;
                        }

                        // Làm mới lại List unavailableSeatList - Thêm những ghế đã được người khác đặt vào trong unavailableSeatList
                        unavailableSeatList.clear();
                        if (value != null) {
                            for (DocumentSnapshot document : value.getDocuments()) {
                                String seat = document.getString("seat");
                                unavailableSeatList.add(seat);
                            }
                        }

                        // Sau khi lấy được chỗ ngồi không khả dụng - tiếp tục lấy vé theo showtime
                        FirebaseUtils.getTicketsByShowtime(showtimeId)
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            System.err.println("Error getting tickets: " + e);
                                            return;
                                        }

                                        // Làm mới List listTickets - Lấy ra số lượng vé còn lại và đưa vào trong listTickets
                                        listTickets.clear();
                                        if (value != null) {
                                            for (DocumentSnapshot document : value.getDocuments()) {
                                                Ticket ticket = document.toObject(Ticket.class);
                                                if (ticket != null) {
                                                    listTickets.add(ticket);
                                                }
                                            }
                                        }

                                        // Xử lý RecyclerView
                                        adapter = new SeatAdapter(list, unavailableSeatList, ChooseSeatActivity.this, txtPrice, txtNumberSeats, listTickets);
                                        rcvListSeat.setAdapter(adapter);
                                    }
                                });
                    }
                });

        // Xử lý layout cho recycler view
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

        // Thực hiện hành động ấn vào nút continue
        btnContinue.setOnClickListener(v -> continueProcess());
    }

    // Hàm xử lý hành động khi ấn vào nút continue
    private void continueProcess() {
        List<String> choosedSeats = adapter.getSelectedSeatList();
        String totalPrice = adapter.getTotalPrice();

        if (choosedSeats.size() > 0) {
            Intent intent = new Intent(this, PaymentActivity.class);

            ArrayList<String> selectedSeatsList = new ArrayList<>(choosedSeats);
            String selectedSeatsString = String.join(",", selectedSeatsList);
            intent.putExtra("SELECTED_SEATS_LIST", selectedSeatsString);
            intent.putExtra("TOTAL_PRICE", totalPrice);
            intent.putExtra("SHOWTIME_ID", showtimeId);

            startActivity(intent);
            setResult(RESULT_OK, intent);
        }
    }
}