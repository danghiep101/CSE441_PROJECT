package com.example.cse441_project.ui.profileoverlay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cse441_project.R;
import com.example.cse441_project.data.model.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText edtUsername, edtEmail, edtPhoneNumber, edtAddress, edtDateOfBirth;
    private RadioGroup radioGroupGender;
    private RadioButton rbMale, rbFemale, rbOther;
    private Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtAddress = findViewById(R.id.edtAddress);
        edtDateOfBirth = findViewById(R.id.edtDateOfBirth);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOther = findViewById(R.id.rbOther);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnChangePassword3);

        loadUserData();

        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveUserData();
            }
        });

        btnDelete.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, DeleteAccountActivity.class);
            startActivity(intent);
        });

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void loadUserData() {
        String uid = mAuth.getCurrentUser().getUid();

        db.collection("users").document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        populateFields(task.getResult());
                    } else {
                        db.collection("admin").document(uid)
                                .get()
                                .addOnCompleteListener(adminTask -> {
                                    if (adminTask.isSuccessful() && adminTask.getResult().exists()) {
                                        populateFields(adminTask.getResult());
                                    } else {
                                        Log.d("ProfileActivity", "User data not found.");
                                        Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
    }

    private void populateFields(DocumentSnapshot document) {
        edtUsername.setText(document.getString("username"));
        edtEmail.setText(document.getString("email"));
        edtPhoneNumber.setText(document.getString("phoneNumber"));
        edtAddress.setText(document.getString("address"));
        edtDateOfBirth.setText(document.getString("dateOfBirth"));

        String gender = document.getString("gender");
        if (gender != null) {
            switch (gender) {
                case "Male":
                    rbMale.setChecked(true);
                    break;
                case "Female":
                    rbFemale.setChecked(true);
                    break;
                case "Other":
                    rbOther.setChecked(true);
                    break;
            }
        }
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(edtUsername.getText())) {
            edtUsername.setError("Username cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(edtEmail.getText())) {
            edtEmail.setError("Email cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(edtPhoneNumber.getText())) {
            edtPhoneNumber.setError("Phone number cannot be empty");
            return false;
        }
        if (TextUtils.isEmpty(edtDateOfBirth.getText())) {
            edtDateOfBirth.setError("Date of birth cannot be empty");
            return false;
        }
        return true;
    }

    private void saveUserData() {
        String uid = mAuth.getCurrentUser().getUid();
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String dateOfBirth = edtDateOfBirth.getText().toString().trim();

        String gender = "";
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId == rbMale.getId()) {
            gender = "Male";
        } else if (selectedGenderId == rbFemale.getId()) {
            gender = "Female";
        } else if (selectedGenderId == rbOther.getId()) {
            gender = "Other";
        }

        User updatedUser = new User(uid, email, username, phoneNumber, null, dateOfBirth);
        updatedUser.setAddress(address);
        updatedUser.setGender(gender);

        db.collection("users").document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        db.collection("users").document(uid).set(updatedUser)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ProfileActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("updatedUsername", username);
                                    setResult(RESULT_OK, resultIntent);
                                    finish(); // Close this activity
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("ProfileActivity", "Error saving data: " + e.getMessage());
                                    Toast.makeText(ProfileActivity.this, "Error saving data.", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        db.collection("admin").document(uid).set(updatedUser)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ProfileActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("updatedUsername", username);
                                    setResult(RESULT_OK, resultIntent);
                                    finish(); // Close this activity
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("ProfileActivity", "Error saving data: " + e.getMessage());
                                    Toast.makeText(ProfileActivity.this, "Error saving data.", Toast.LENGTH_SHORT).show();
                                });
                    }
                });
    }
}
