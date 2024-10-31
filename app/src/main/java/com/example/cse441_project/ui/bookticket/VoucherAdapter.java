package com.example.cse441_project.ui.bookticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cse441_project.R;
import com.example.cse441_project.data.model.discount.Discount;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private List<Discount> discounts = new ArrayList<>();
    private int selectedPosition = -1; // Trạng thái chọn
    private String selectedId = "";
    private OnItemSelectedListener onItemSelectedListener; // Listener để thông báo

    // Interface để thông báo khi một voucher được chọn
    public interface OnItemSelectedListener {
        void onItemSelected(String selectedId, String selectedValue);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Discount discount = discounts.get(position);
        holder.voucherName.setText(discount.getName());
        holder.voucherExpiry.setText("Expires: " + discount.getValue());

        // Đổi màu nền khi item được chọn
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(0xFFCCCCCC); // Màu #CCC khi chọn
        } else {
            holder.itemView.setBackgroundColor(0xFFFFFFFF); // Màu trắng khi không chọn
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            selectedId = discount.getId(); // Lưu lại ID của phiếu giảm giá đã chọn
            String expiryText = holder.voucherExpiry.getText().toString();
            String expiryNumber = expiryText.replaceAll("[^0-9]", "");
            // Gọi listener để thông báo về voucher được chọn
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(selectedId, expiryNumber); // Gửi ID và giá trị
            }

            notifyDataSetChanged(); // Cập nhật toàn bộ RecyclerView
        });
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView voucherName, voucherExpiry;

        VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherName = itemView.findViewById(R.id.txt_voucher_name);
            voucherExpiry = itemView.findViewById(R.id.txt_voucher_expries);
        }
    }
}
