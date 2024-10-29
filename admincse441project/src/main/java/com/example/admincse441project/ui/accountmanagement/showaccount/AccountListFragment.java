package com.example.admincse441project.ui.accountmanagement.showaccount;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.account.Account;
import com.example.admincse441project.ui.accountmanagement.edit.EditAccountActivity;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class AccountListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;
    private List<Account> accountList;
    private EditText edtSearchAccount;

    private static final int EDIT_ACCOUNT_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        edtSearchAccount = view.findViewById(R.id.edt_search_account2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accountList = new ArrayList<>();
        accountAdapter = new AccountAdapter(accountList);
        recyclerView.setAdapter(accountAdapter);

        loadAccounts("");

        edtSearchAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadAccounts(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        accountAdapter.setOnItemClickListener(account -> {
            if (!account.isAdmin()) {
                // Nếu tài khoản là User, hiện Toast thông báo không có quyền chỉnh sửa
                Toast.makeText(getContext(), "This account does not have permission to edit", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu tài khoản là Admin, mở EditAccountActivity
                Intent intent = new Intent(getContext(), EditAccountActivity.class);
                intent.putExtra("ACCOUNT_ID", account.getUid()); // Truyền ID thực sự từ Firestore
                Log.d("AccountListFragment", "Sending ACCOUNT_ID: " + account.getUid()); // In log để kiểm tra ID
                startActivityForResult(intent, EDIT_ACCOUNT_REQUEST_CODE); // Sử dụng startActivityForResult
            }
        });

        return view;
    }

    private void loadAccounts(String query) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        accountList.clear();

        // Truy vấn collection "admin"
        db.collection("admin").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Account account = document.toObject(Account.class);
                    if (account != null) {
                        account.setUid(document.getId()); // Gán ID Firestore vào trường id của Account
                        account.setAdmin(true); // Thiết lập role là Admin
                        accountList.add(account);
                    }
                }
                accountAdapter.notifyDataSetChanged();
            }
        });

        // Truy vấn tiếp collection "users" sau khi đã lấy xong "admin"
        db.collection("users").get().addOnCompleteListener(userTask -> {
            if (userTask.isSuccessful() && userTask.getResult() != null) {
                for (DocumentSnapshot document : userTask.getResult()) {
                    Account account = document.toObject(Account.class);
                    if (account != null) {
                        account.setUid(document.getId()); // Sử dụng ID của Firestore làm ID của account
                        account.setAdmin(false); // Thiết lập role là User
                        accountList.add(account);
                    }
                }
                accountAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView sau khi có đủ dữ liệu
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra nếu là yêu cầu từ EditAccountActivity và có kết quả thành công
        if (requestCode == EDIT_ACCOUNT_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            loadAccounts(""); // Tải lại danh sách tài khoản sau khi cập nhật
        }
    }
}

