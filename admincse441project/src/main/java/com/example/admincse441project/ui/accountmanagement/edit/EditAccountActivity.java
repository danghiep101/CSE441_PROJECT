package com.example.admincse441project.ui.accountmanagement.edit;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.account.Account;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends AppCompatActivity{

    private EditText edtPhoneNumber, edtEmail, edtUsername, edtDateOfBirth;
    private Switch switchAdmin;
    private Button btnSave;
    private String accountId;
    private String currentCollection;
    private boolean originalAdminStatus; // Trạng thái ban đầu của switch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtDateOfBirth = findViewById(R.id.edtDateOfBirth);
        switchAdmin = findViewById(R.id.switchAdmin);
        btnSave = findViewById(R.id.btnSave);

        accountId = getIntent().getStringExtra("ACCOUNT_ID");
        Log.d("EditAccountActivity", "Received ACCOUNT_ID: " + accountId);

        if (accountId != null) {
            displayAccountInfo(accountId);
        } else {
            Toast.makeText(this, "Account ID not found", Toast.LENGTH_SHORT).show();
        }

        btnSave.setOnClickListener(v -> updateAccountInfo());

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void displayAccountInfo(String accountId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Kiểm tra trong collection "admin"
        db.collection("admin").document(accountId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Account account = documentSnapshot.toObject(Account.class);
                        if (account != null) {
                            populateFields(account);
                            switchAdmin.setChecked(true);
                            originalAdminStatus = true; // Ghi nhận trạng thái ban đầu
                            currentCollection = "admin";
                        }
                    } else {
                        db.collection("users").document(accountId).get()
                                .addOnSuccessListener(userDoc -> {
                                    if (userDoc.exists()) {
                                        Account account = userDoc.toObject(Account.class);
                                        if (account != null) {
                                            populateFields(account);
                                            switchAdmin.setChecked(false);
                                            originalAdminStatus = false;
                                            currentCollection = "users";
                                        }
                                    } else {
                                        Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error fetching account details", Toast.LENGTH_SHORT).show();
                    Log.e("EditAccountActivity", "Error fetching account", e);
                });
    }

    private void populateFields(Account account) {
        edtPhoneNumber.setText(account.getPhoneNumber());
        edtEmail.setText(account.getEmail());
        edtUsername.setText(account.getUsername());
        edtDateOfBirth.setText(account.getDateOfBirth());
    }

    private void updateAccountInfo() {
        String phoneNumber = edtPhoneNumber.getText().toString();
        String email = edtEmail.getText().toString();
        String username = edtUsername.getText().toString();
        String dateOfBirth = edtDateOfBirth.getText().toString();
        boolean isAdmin = switchAdmin.isChecked();

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(dateOfBirth)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(currentCollection).document(accountId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> accountData = new HashMap<>(documentSnapshot.getData());

                accountData.put("phoneNumber", phoneNumber);
                accountData.put("email", email);
                accountData.put("username", username);
                accountData.put("dateOfBirth", dateOfBirth);

                String targetCollection = isAdmin ? "admin" : "users";

                if (originalAdminStatus != isAdmin) {
                    db.collection(currentCollection).document(accountId)
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                db.collection(targetCollection).document(accountId)
                                        .set(accountData)
                                        .addOnSuccessListener(aVoid1 -> {
                                            Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();
                                            setResult(RESULT_OK);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(this, "Failed to update account", Toast.LENGTH_SHORT).show());
                            })
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete old account data", Toast.LENGTH_SHORT).show());
                } else {
                    db.collection(currentCollection).document(accountId)
                            .set(accountData)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Account updated successfully", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(this, "Failed to update account", Toast.LENGTH_SHORT).show());
                }
            } else {
                Toast.makeText(this, "Account data not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Failed to fetch account data", Toast.LENGTH_SHORT).show());
    }
}

