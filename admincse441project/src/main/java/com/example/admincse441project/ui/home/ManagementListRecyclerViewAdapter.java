package com.example.admincse441project.ui.home;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.Management;
import com.example.admincse441project.ui.accountmanagement.showaccount.AccountListFragment;
import com.example.admincse441project.ui.discountmanagement.showdiscount.DiscountsListFragment;
import com.example.admincse441project.ui.showtimemaganement.ShowTimeListFragment;
import com.example.admincse441project.ui.ticketmanagement.list.TicketListFragment;


import java.util.List;

public class ManagementListRecyclerViewAdapter extends RecyclerView.Adapter<ManagementListRecyclerViewAdapter.ViewHolder> {
    private List<Management> managementList;
    private int selectedPosition = 0;
    private FragmentManager fragmentManager;

    public ManagementListRecyclerViewAdapter (List<Management> managementList, FragmentManager fragmentManager) {
        this.managementList = managementList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section_management, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Management managementItem = managementList.get(position);
        holder.txt_name.setText(managementItem.getName());

        if (position == selectedPosition) {
            holder.txt_name.setTextColor(holder.itemView.getContext().getColor(R.color.button_gradiant_2));
            holder.txt_name.setBackgroundTintList(ColorStateList.valueOf(holder.itemView.getContext().getColor(R.color.opacity_button_gradiant_2)));
        } else {
            holder.txt_name.setTextColor(0xFFADADAD);
            holder.txt_name.setBackgroundTintList(ColorStateList.valueOf(holder.itemView.getContext().getColor(R.color.background_controller)));
        }

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            // Tạo Fragment mới dựa trên item được chọn
            Fragment newFragment = getFragmentForItem(selectedPosition);
            if (newFragment != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, newFragment)
                        .addToBackStack(null)  // Cho phép quay lại fragment trước
                        .commit();
            }
        });
    }

    private Fragment getFragmentForItem(int index) {
        switch (index) {
            case 0:
                return new AccountListFragment();
            case 1:
                return new DiscountsListFragment();
            case 3:
                return new TicketListFragment();
            case 4:
                return new ShowTimeListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return managementList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView txt_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
        }
    }
}