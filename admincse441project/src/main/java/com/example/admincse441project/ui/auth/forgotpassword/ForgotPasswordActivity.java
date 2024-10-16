package com.example.admincse441project.ui.auth.forgotpassword;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.admincse441project.R;
import com.example.admincse441project.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        onClickView();
        observeViewModel();
    }

    private void onClickView() {
        binding.btnSend.setOnClickListener(v -> {
            String userEmail = binding.etEmail.getText().toString().trim();
            if(!userEmail.isEmpty()){
                viewModel.resetPassword(userEmail);
            }else {
                binding.etEmail.setError(binding.etEmail.getHint() + "is required");
            }
        });
        binding.btnBack.setOnClickListener(v-> {
            finish();
        });
    }

    private void observeViewModel() {
        viewModel.resetStatus.observe(this, isSuccess ->{
            if (isSuccess){
                Toast.makeText(this, "Password reset email sent", Toast.LENGTH_LONG).show();
                binding.btnSend.setActivated(false);

            }else{
                Toast.makeText(this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
            }
        });

    }
}