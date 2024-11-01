package com.example.cse441_project.ui.profileoverlay;

import static android.content.ContentValues.TAG;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.auth.login.LoginActivity;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AcceptDeleteAccountActivity extends AppCompatActivity {

    private EditText edtPassword;
    private Button btnDelete;
    private ImageView btnBack;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "DeleteAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_delete_account);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtPassword = findViewById(R.id.password);
        btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(v -> deleteAccount());

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        Log.d(TAG, "onCreate: AcceptDeleteAccountActivity started");
    }

    private void deleteAccount() {
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Password is required");
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Log.e("DeleteAccount", "No user logged in");
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("DeleteAccount", "Starting reauthentication process");
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), edtPassword.getText().toString().trim());

        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("DeleteAccount", "Reauthentication successful");
                deleteUserFromFirestore(user.getUid()); // Tiếp tục với việc xóa Firestore
            } else {
                Log.e("DeleteAccount", "Reauthentication failed", task.getException());
                Toast.makeText(AcceptDeleteAccountActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteUserFromFirestore(String uid) {
        Log.d("DeleteAccount", "Attempting to delete user data from Firestore for UID: " + uid);

        db.collection("users").document(uid).get().addOnCompleteListener(userTask -> {
            if (userTask.isSuccessful() && userTask.getResult().exists()) {
                // Nếu tồn tại trong "users", tiến hành xóa
                db.collection("users").document(uid).delete()
                        .addOnSuccessListener(aVoid -> {
                            Log.d("DeleteAccount", "User data deleted from 'users' collection.");
                            deleteUserAuth(); // Tiếp tục xóa trong Firebase Auth
                        })
                        .addOnFailureListener(e -> {
                            Log.e("DeleteAccount", "Failed to delete user data from 'users' collection", e);
                            Toast.makeText(this, "Failed to delete account data", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Log.d("DeleteAccount", "User not found in 'users', checking 'admin' collection.");
                db.collection("admin").document(uid).get().addOnCompleteListener(adminTask -> {
                    if (adminTask.isSuccessful() && adminTask.getResult().exists()) {
                        // Nếu tồn tại trong "admin", tiến hành xóa
                        db.collection("admin").document(uid).delete()
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("DeleteAccount", "User data deleted from 'admin' collection.");
                                    deleteUserAuth(); // Tiếp tục xóa trong Firebase Auth
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("DeleteAccount", "Failed to delete account data from 'admin' collection", e);
                                    Toast.makeText(this, "Failed to delete account data", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Log.e("DeleteAccount", "User not found in both 'users' and 'admin' collections.");
                        Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    private void deleteUserAuth() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d("DeleteAccount", "Deleting user from Firebase Auth");
            user.delete().addOnCompleteListener(deleteTask -> {
                if (deleteTask.isSuccessful()) {
                    Log.d("DeleteAccount", "User deletion from Firebase Auth successful");
                    onAccountDeleted();
                } else {
                    Log.e("DeleteAccount", "Account deletion from Firebase Auth failed", deleteTask.getException());
                    Toast.makeText(AcceptDeleteAccountActivity.this, "Account deletion failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e("DeleteAccount", "User is null during deleteUserAuth");
        }
    }

    private void onAccountDeleted() {
        Log.d("DeleteAccount", "Account deleted successfully, navigating to login screen.");
        Toast.makeText(AcceptDeleteAccountActivity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AcceptDeleteAccountActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
