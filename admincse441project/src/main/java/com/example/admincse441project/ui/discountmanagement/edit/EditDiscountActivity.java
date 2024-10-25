package com.example.admincse441project.ui.discountmanagement.edit;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.admincse441project.R;
import com.example.admincse441project.databinding.ActivityEditDiscountBinding;

public class EditDiscountActivity extends AppCompatActivity {
    private ActivityEditDiscountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDiscountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}