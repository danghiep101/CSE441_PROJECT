package com.example.cse441_project.ui.bookticket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.cse441_project.R;
import com.example.cse441_project.ui.ggpay.activity.CheckoutActivity;

public class PaymentActivity extends AppCompatActivity {
    private String showTimeId;
    private SelectShowTime viewModel;
    private TextView nameMovie, timeMovie, Seat, Total;
    private Button voucher, ticket;
    private double newTotalPrice;
    private String selectedSeatsString;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        voucher = findViewById(R.id.btn_choose_promotion);
        ticket = findViewById(R.id.btn_book_ticket_payment);

        voucher.setOnClickListener(v -> continueProcess());
        ticket.setOnClickListener(v -> bookTicket());
        viewModel = new ViewModelProvider(this).get(SelectShowTime.class);
        showTimeId = getIntent().getStringExtra("SHOWTIME_ID");

        initializeViews();
        fetchShowTime(showTimeId);
    }

    private void bookTicket() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        intent.putExtra("TOTAL_PRICE" , newTotalPrice);
        intent.putExtra("SHOWTIME_ID" , showTimeId);
        intent.putExtra("SELECTED_SEATS_LIST" , selectedSeatsString);
        startActivity(intent); // Sử dụng startActivityForResult để nhận dữ liệu
    }

    private void continueProcess() {
        Intent intent = new Intent(this, ChooseVoucherActivity.class);
        startActivityForResult(intent, 1); // Sử dụng startActivityForResult để nhận dữ liệu
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Nhận giá trị voucher từ ChooseVoucherActivity
            String voucherValue = data.getStringExtra("voucher_value");
            if (voucherValue != null) {
                updateTotalPrice(voucherValue);

            }
        }
    }

    private void initializeViews() {
        nameMovie = findViewById(R.id.tvMovieName);
        timeMovie = findViewById(R.id.tvTimeDate);
        Seat = findViewById(R.id.seat);
        Total = findViewById(R.id.total);
    }

    private void fetchShowTime(String showTimeId) {
        selectedSeatsString = getIntent().getStringExtra("SELECTED_SEATS_LIST");
        String totalPrice = getIntent().getStringExtra("TOTAL_PRICE");
        viewModel.getShowTime(showTimeId).observe(this, showTime -> {
            nameMovie.setText(showTime.getName());
            timeMovie.setText(showTime.getDate() + "-" + showTime.getStartTime());
            Seat.setText(selectedSeatsString);
            Total.setText(totalPrice); // Hiển thị tổng tiền ban đầu
        });

    }

    private void updateTotalPrice(String voucherValue) {
        // Lấy giá trị tổng tiền hiện tại
        String totalPriceText = Total.getText().toString();
        double totalPrice = Double.parseDouble(totalPriceText);

        // Lấy giá trị voucher
        double discountValue = Double.parseDouble(voucherValue);

        newTotalPrice = totalPrice * (1 - discountValue / 100.0);

        // Cập nhật giao diện
        Total.setText(String.valueOf(newTotalPrice)); // Cập nhật lại tổng tiền
    }
}
