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
                Toast.makeText(getContext(), "This account does not have permission to edit", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getContext(), EditAccountActivity.class);
                intent.putExtra("ACCOUNT_ID", account.getUid());
                Log.d("AccountListFragment", "Sending ACCOUNT_ID: " + account.getUid());
                startActivityForResult(intent, EDIT_ACCOUNT_REQUEST_CODE);
            }
        });

        return view;
    }

    private void loadAccounts(String query) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        accountList.clear();

        db.collection("admin").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Account account = document.toObject(Account.class);
                    if (account != null) {
                        account.setUid(document.getId());
                        account.setAdmin(true);
                        accountList.add(account);
                    }
                }
                accountAdapter.notifyDataSetChanged();
            }
        });

        db.collection("users").get().addOnCompleteListener(userTask -> {
            if (userTask.isSuccessful() && userTask.getResult() != null) {
                for (DocumentSnapshot document : userTask.getResult()) {
                    Account account = document.toObject(Account.class);
                    if (account != null) {
                        account.setUid(document.getId());
                        account.setAdmin(false);
                        accountList.add(account);
                    }
                }
                accountAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_ACCOUNT_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            loadAccounts("");
        }
    }
}