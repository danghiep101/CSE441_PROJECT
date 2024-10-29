package com.example.cse441_project.ui.profileoverlay;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cse441_project.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private EditText edtUsername, edtEmail, edtPhoneNumber, edtAddress, edtDateOfBirth;
    private RadioGroup radioGroupGender;
    private Button btnSave;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Khởi tạo các view
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtAddress = findViewById(R.id.edtAddress);
        edtDateOfBirth = findViewById(R.id.edtDateOfBirth);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnSave = findViewById(R.id.btnSave);

        // Lấy userId từ Intent
        userId = getIntent().getStringExtra("USER_ID");

        // Tải dữ liệu người dùng
        if (userId != null) {
            loadUserData();
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }

        // Sự kiện click lưu dữ liệu
        btnSave.setOnClickListener(v -> updateUserData());
    }

    private void loadUserData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        String email = documentSnapshot.getString("email");
                        String phoneNumber = documentSnapshot.getString("phoneNumber");
                        String address = documentSnapshot.getString("address");
                        String dateOfBirth = documentSnapshot.getString("dateOfBirth");
                        String gender = documentSnapshot.getString("gender");

                        edtUsername.setText(username);
                        edtEmail.setText(email);
                        edtPhoneNumber.setText(phoneNumber);
                        edtAddress.setText(address);
                        edtDateOfBirth.setText(dateOfBirth);

                        if ("Male".equals(gender)) {
                            radioGroupGender.check(R.id.rbMale);
                        } else if ("Female".equals(gender)) {
                            radioGroupGender.check(R.id.rbFemale);
                        } else {
                            radioGroupGender.check(R.id.rbOther);
                        }
                    } else {
                        Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show());
    }

    private void updateUserData() {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String dateOfBirth = edtDateOfBirth.getText().toString().trim();

        // Lấy giá trị giới tính
        String gender;
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId == R.id.rbMale) {
            gender = "Male";
        } else if (selectedGenderId == R.id.rbFemale) {
            gender = "Female";
        } else {
            gender = "Other";
        }

        // Tạo một HashMap chứa dữ liệu mới
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("phoneNumber", phoneNumber);
        userData.put("address", address);
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("gender", gender);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show());
    }
}
