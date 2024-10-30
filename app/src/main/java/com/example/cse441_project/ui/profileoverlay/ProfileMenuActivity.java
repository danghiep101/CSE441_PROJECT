package com.example.cse441_project.ui.profileoverlay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.home.HomeActivity;
import com.example.cse441_project.ui.profileoverlay.ProfileActivity;
import com.example.cse441_project.ui.auth.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class ProfileMenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView tvHi, tvName;
    private Button btnProfile, btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvHi = findViewById(R.id.tvHi);
        tvName = findViewById(R.id.tvName);
        btnProfile = findViewById(R.id.btnProfile);
        btnSignOut = findViewById(R.id.btnSignOut);

        fetchUsername();

        ImageView btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> onBackPressed());

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileMenuActivity.this, ProfileActivity.class);
            startActivityForResult(intent, 1);
        });

        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(ProfileMenuActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String updatedUsername = data.getStringExtra("updatedUsername");
            if (updatedUsername != null) {
                tvName.setText(updatedUsername);
            }
        }
    }

    private void fetchUsername() {
        String uid = mAuth.getCurrentUser().getUid();
        Log.d("ProfileMenuActivity", "Current User UID: " + uid);

        db.collection("users").whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String username = document.getString("username");
                        Log.d("ProfileMenuActivity", "Username from users: " + username);
                        tvName.setText(username);
                    } else {
                        db.collection("admin").whereEqualTo("uid", uid)
                                .get()
                                .addOnCompleteListener(adminTask -> {
                                    if (adminTask.isSuccessful() && !adminTask.getResult().isEmpty()) {
                                        DocumentSnapshot document = adminTask.getResult().getDocuments().get(0);
                                        String username = document.getString("username");
                                        Log.d("ProfileMenuActivity", "Username from admin: " + username);
                                        tvName.setText(username);
                                    } else {
                                        Log.d("ProfileMenuActivity", "No matching document in admin collection.");
                                    }
                                });
                    }
                });
    }
}

