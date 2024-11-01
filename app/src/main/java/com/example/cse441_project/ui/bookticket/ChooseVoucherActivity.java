package com.example.cse441_project.ui.bookticket;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cse441_project.R;
import com.example.cse441_project.data.model.discount.Discount;
import com.example.cse441_project.ui.bookticket.DiscountViewModel;
import com.example.cse441_project.ui.bookticket.VoucherAdapter;

import java.util.List;

public class ChooseVoucherActivity extends AppCompatActivity {
    private DiscountViewModel discountViewModel;
    private RecyclerView recyclerView;
    private VoucherAdapter adapter;
    private Button btnUseVoucher;
    private String selectedVoucherId = "";
    private String selectedVoucherValue = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_voucher);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.rcv_voucher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VoucherAdapter();
        recyclerView.setAdapter(adapter);

        // Khởi tạo ViewModel
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        discountViewModel.discounts.observe(this, discounts -> {
            // Cập nhật dữ liệu trong Adapter khi dữ liệu thay đổi
            adapter.setDiscounts(discounts);
        });

        // Khởi tạo nút sử dụng voucher
        btnUseVoucher = findViewById(R.id.btnUseVoucher);
        btnUseVoucher.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("voucher_value", selectedVoucherValue); // Gửi giá trị voucher
            setResult(RESULT_OK, resultIntent);
            finish(); // Kết thúc activity và trả về kết quả
        });

        // Thiết lập listener để nhận ID và giá trị voucher đã chọn
        adapter.setOnItemSelectedListener((selectedId, selectedValue) -> {
            selectedVoucherId = selectedId;
            selectedVoucherValue = selectedValue;
        });
    }
}
