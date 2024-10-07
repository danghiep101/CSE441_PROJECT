package com.example.cse441_project.ui.bookticket;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse441_project.R;

import java.util.HashMap;
import java.util.Map;

public class ChooseSeatActivity extends AppCompatActivity {

    // Lưu trạng thái của từng ghế (true = đã chọn, false = chưa chọn)
    private Map<Button, Boolean> seatStatus = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);

        // Tìm và thêm các ghế vào Map với trạng thái ban đầu là false (chưa chọn)
        Button seatA1 = findViewById(R.id.seatA1);
        Button seatA2 = findViewById(R.id.seatA2);
        Button seatA3 = findViewById(R.id.seatA3);
        Button seatA4 = findViewById(R.id.seatA4);
        Button seatA5 = findViewById(R.id.seatA5);
        Button seatA6 = findViewById(R.id.seatA6);

        Button seatB1 = findViewById(R.id.seatB1);
        Button seatB2 = findViewById(R.id.seatB2);
        Button seatB3 = findViewById(R.id.seatB3);
        Button seatB4 = findViewById(R.id.seatB4);
        Button seatB5 = findViewById(R.id.seatB5);
        Button seatB6 = findViewById(R.id.seatB6);

        Button seatC1 = findViewById(R.id.seatC1);
        Button seatC2 = findViewById(R.id.seatC2);
        Button seatC3 = findViewById(R.id.seatC3);
        Button seatC4 = findViewById(R.id.seatC4);
        Button seatC5 = findViewById(R.id.seatC5);
        Button seatC6 = findViewById(R.id.seatC6);

        Button seatD1 = findViewById(R.id.seatD1);
        Button seatD2 = findViewById(R.id.seatD2);
        Button seatD3 = findViewById(R.id.seatD3);
        Button seatD4 = findViewById(R.id.seatD4);
        Button seatD5 = findViewById(R.id.seatD5);
        Button seatD6 = findViewById(R.id.seatD6);

        Button seatE1 = findViewById(R.id.seatE1);
        Button seatE2 = findViewById(R.id.seatE2);
        Button seatE3 = findViewById(R.id.seatE3);
        Button seatE4 = findViewById(R.id.seatE4);
        Button seatE5 = findViewById(R.id.seatE5);
        Button seatE6 = findViewById(R.id.seatE6);

        Button seatF1 = findViewById(R.id.seatF1);
        Button seatF2 = findViewById(R.id.seatF2);
        Button seatF3 = findViewById(R.id.seatF3);
        Button seatF4 = findViewById(R.id.seatF4);
        Button seatF5 = findViewById(R.id.seatF5);
        Button seatF6 = findViewById(R.id.seatF6);

        // Đặt trạng thái mặc định cho tất cả các ghế là chưa chọn
        seatStatus.put(seatA1, false);
        seatStatus.put(seatA2, false);
        seatStatus.put(seatA3, false);
        seatStatus.put(seatA4, false);
        seatStatus.put(seatA5, false);
        seatStatus.put(seatA6, false);

        seatStatus.put(seatB1, false);
        seatStatus.put(seatB2, false);
        seatStatus.put(seatB3, false);
        seatStatus.put(seatB4, false);
        seatStatus.put(seatB5, false);
        seatStatus.put(seatB6, false);

        seatStatus.put(seatC1, false);
        seatStatus.put(seatC2, false);
        seatStatus.put(seatC3, false);
        seatStatus.put(seatC4, false);
        seatStatus.put(seatC5, false);
        seatStatus.put(seatC6, false);

        seatStatus.put(seatD1, false);
        seatStatus.put(seatD2, false);
        seatStatus.put(seatD3, false);
        seatStatus.put(seatD4, false);
        seatStatus.put(seatD5, false);
        seatStatus.put(seatD6, false);

        seatStatus.put(seatE1, false);
        seatStatus.put(seatE2, false);
        seatStatus.put(seatE3, false);
        seatStatus.put(seatE4, false);
        seatStatus.put(seatE5, false);
        seatStatus.put(seatE6, false);

        seatStatus.put(seatF1, false);
        seatStatus.put(seatF2, false);
        seatStatus.put(seatF3, false);
        seatStatus.put(seatF4, false);
        seatStatus.put(seatF5, false);
        seatStatus.put(seatF6, false);

        // Đặt sự kiện onClickListener cho mỗi ghế
        setupSeatButton(seatA1);
        setupSeatButton(seatA2);
        setupSeatButton(seatA3);
        setupSeatButton(seatA4);
        setupSeatButton(seatA5);
        setupSeatButton(seatA6);

        setupSeatButton(seatB1);
        setupSeatButton(seatB2);
        setupSeatButton(seatB3);
        setupSeatButton(seatB4);
        setupSeatButton(seatB5);
        setupSeatButton(seatB6);

        setupSeatButton(seatC1);
        setupSeatButton(seatC2);
        setupSeatButton(seatC3);
        setupSeatButton(seatC4);
        setupSeatButton(seatC5);
        setupSeatButton(seatC6);

        setupSeatButton(seatD1);
        setupSeatButton(seatD2);
        setupSeatButton(seatD3);
        setupSeatButton(seatD4);
        setupSeatButton(seatD5);
        setupSeatButton(seatD6);

        setupSeatButton(seatE1);
        setupSeatButton(seatE2);
        setupSeatButton(seatE3);
        setupSeatButton(seatE4);
        setupSeatButton(seatE5);
        setupSeatButton(seatE6);

        setupSeatButton(seatF1);
        setupSeatButton(seatF2);
        setupSeatButton(seatF3);
        setupSeatButton(seatF4);
        setupSeatButton(seatF5);
        setupSeatButton(seatF6);
    }

    // Thiết lập sự kiện cho từng ghế
    private void setupSeatButton(Button seat) {
        seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = seatStatus.get(seat); // Lấy trạng thái hiện tại của ghế
                if (isSelected) {
                    // Ghế đang được chọn -> đổi thành chưa chọn
                    seat.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFB6C1"))); // Màu ghế chưa chọn (hồng)
                } else {
                    // Ghế chưa được chọn -> đổi thành đã chọn
                    seat.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000"))); // Màu ghế đã chọn (đỏ)
                }
                // Cập nhật trạng thái của ghế
                seatStatus.put(seat, !isSelected);
            }
        });
    }
}