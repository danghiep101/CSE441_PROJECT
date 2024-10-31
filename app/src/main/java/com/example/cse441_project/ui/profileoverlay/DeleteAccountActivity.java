package com.example.cse441_project.ui.profileoverlay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse441_project.R;

public class DeleteAccountActivity extends AppCompatActivity {

    private Button btnDelete, btnCancel;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> onBackPressed());

        btnCancel.setOnClickListener(v -> onBackPressed());

        btnDelete.setOnClickListener(v -> {
            Intent intent = new Intent(DeleteAccountActivity.this, AcceptDeleteAccountActivity.class);
            startActivity(intent);
        });
    }
}

