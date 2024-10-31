package com.example.cse441_project.ui.bookticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.cse441_project.R;

public class PaymentActivity extends AppCompatActivity {
    private String showTimeId;
    private SelectShowTime viewModel;
    private TextView nameMovie, timeMovie, Seat, Total;
    private Button voucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        voucher = findViewById(R.id.btn_choose_promotion);
        voucher.setOnClickListener(v -> continueProcess());

        viewModel = new ViewModelProvider(this).get(SelectShowTime.class);
        showTimeId = getIntent().getStringExtra("SHOWTIME_ID");

        initializeViews();
        fetchShowTime(showTimeId);
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
        String selectedSeatsString = getIntent().getStringExtra("SELECTED_SEATS_LIST");
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

        // Tính toán lại tổng tiền sau khi trừ đi voucher
        double newTotalPrice = totalPrice - discountValue;

        // Cập nhật giao diện
        Total.setText(String.valueOf(newTotalPrice)); // Cập nhật lại tổng tiền
    }
}
