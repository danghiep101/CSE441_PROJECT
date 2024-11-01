package com.example.cse441_project.ui.auth.passwordreset;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.R;
import com.example.cse441_project.databinding.ActivityPasswordResetBinding;

public class PasswordResetActivity extends AppCompatActivity {
    private ActivityPasswordResetBinding binding;
    private PasswordResetViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(PasswordResetViewModel.class);
        onClickView();
        observeViewModel();
    }

    private void onClickView() {
        binding.btnSendEmail.setOnClickListener(v -> {
            String userEmail = binding.etEmail.getText().toString().trim();
            if(!userEmail.isEmpty()){
                viewModel.resetPassword(userEmail);
            }else {
                binding.etEmail.setError(binding.etEmail.getHint() + "is required");
            }
        });
        binding.btnBackLogin.setOnClickListener(v-> {
            finish();
        });
    }

    private void observeViewModel() {
        viewModel.resetStatus.observe(this, isSuccess ->{
            if (isSuccess){
                Toast.makeText(this, "Password reset email sent", Toast.LENGTH_LONG).show();
                binding.btnSendEmail.setActivated(false);

            }else{
                Toast.makeText(this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
            }
        });

    }
}