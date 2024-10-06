package com.example.cse441_project.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse441_project.databinding.ActivityMainBinding;
import com.example.cse441_project.ui.profileoverlay.ProfileActivity;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }


}