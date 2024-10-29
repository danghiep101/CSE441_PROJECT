package com.example.cse441_project.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.auth.login.LoginActivity;
import com.example.cse441_project.ui.home.HomeActivity;
import com.example.cse441_project.utils.FirebaseUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (FirebaseUtils.currentUserId() != null) {

            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        } else {

            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }
}