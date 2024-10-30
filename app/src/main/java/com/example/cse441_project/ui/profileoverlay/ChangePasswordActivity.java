package com.example.cse441_project.ui.profileoverlay;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse441_project.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private Button btnChangePassword;
    private ImageView btnBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();

        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChange);
        btnBack = findViewById(R.id.btnBack); // Nút quay lại

        btnChangePassword.setOnClickListener(v -> changePassword());

        // Xử lý sự kiện khi nhấn nút quay lại
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void changePassword() {
        String currentPassword = etCurrentPassword.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Kiểm tra nếu trường nhập trống
        if (TextUtils.isEmpty(currentPassword)) {
            etCurrentPassword.setError("Current password is required");
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("New password is required");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Please confirm your new password");
            return;
        }

        // Kiểm tra độ dài của mật khẩu mới
        if (newPassword.length() < 8) {
            etNewPassword.setError("New password must be at least 8 characters");
            return;
        }

        // Kiểm tra mật khẩu mới và mật khẩu xác nhận phải trùng nhau
        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        // Xác thực người dùng với mật khẩu hiện tại
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("ChangePassword", "Reauthentication successful");
                    // Cập nhật mật khẩu mới
                    user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Đóng activity và quay về màn hình trước
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Password change failed", Toast.LENGTH_SHORT).show();
                            Log.e("ChangePassword", "Password update failed", updateTask.getException());
                        }
                    });
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Authentication failed. Please check your current password.", Toast.LENGTH_SHORT).show();
                    Log.e("ChangePassword", "Reauthentication failed", task.getException());
                }
            });
        }
    }
}
