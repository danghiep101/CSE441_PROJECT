package com.example.admincse441project.ui.accountmanagement.showaccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.account.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<Account> accountList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Account account);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AccountAdapter(List<Account> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_row, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView tvNo, tvEmail, tvUsername, tvRole;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNo = itemView.findViewById(R.id.tvNo);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvRole = itemView.findViewById(R.id.tvRole);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvEmail.setText(account.getEmail());
        holder.tvUsername.setText(account.getUsername());
        holder.tvRole.setText(account.getRole());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(account);
            }
        });
    }

}
