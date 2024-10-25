package com.example.admincse441project.ui.discountmanagement.showdiscount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.databinding.ItemDiscountRowBinding;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    private List<Discount> discountList;
    private OnDiscountClickListener listener;
    public DiscountAdapter(List<Discount> discountList, OnDiscountClickListener listener) {
        this.discountList = discountList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public DiscountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDiscountRowBinding binding = ItemDiscountRowBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountAdapter.ViewHolder holder, int position) {
        Discount discount = discountList.get(position);
        holder.bind(discount);
        holder.itemView.setOnClickListener(v -> listener.onDiscountClick(discount));
    }

    @Override
    public int getItemCount() {
        return discountList != null ? discountList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDiscountRowBinding binding;

        public ViewHolder(ItemDiscountRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Discount discount) {
            binding.txtDiscountName.setText(discount.getName());
            binding.txtDiscountQuantity.setText(String.valueOf(discount.getQuantity()));
            binding.txtDiscountValue.setText(String.valueOf(discount.getValue()));
        }
    }
    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
        notifyDataSetChanged();
    }
    public interface OnDiscountClickListener {
        void onDiscountClick(Discount discount);
    }
}
