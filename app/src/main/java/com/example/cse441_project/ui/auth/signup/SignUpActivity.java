package com.example.cse441_project.ui.auth.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.databinding.ActivitySignUpBinding;
import com.example.cse441_project.ui.auth.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        setContentView(binding.getRoot());

        onClickView();
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.signUpStatus.observe(this, isSuccess -> {
            if (isSuccess) {
                String userEmail = binding.etEmail.getText().toString().trim();
                String userName = binding.etUsername.getText().toString().trim();
                String phoneNumber = binding.etPhoneNumber.getText().toString().trim();
                String dateOfBirth = binding.etDateOfBirth.getText().toString().trim(); // Sửa ở đây
                viewModel.saveUserDetail(userName, userEmail, dateOfBirth, phoneNumber);
            } else {
                Toast.makeText(this, "Cannot Sign Up", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.saveUserDetailStatus.observe(this, isSuccess -> {
            if (isSuccess) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(this, "Sign Up Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Sign Up failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onClickView() {
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnCreate.setOnClickListener(v -> {
            signUp();
        });
    }

    private void signUp() {
        String userEmail = binding.etEmail.getText().toString().trim();
        String userPassword = binding.etPassword.getText().toString().trim();
        String userName = binding.etUsername.getText().toString().trim();
        String dateOfBirth = binding.etDateOfBirth.getText().toString().trim();
        String phoneNumber = binding.etPhoneNumber.getText().toString().trim();

        if (viewModel.notEmpty(userEmail, userName, userPassword, dateOfBirth, phoneNumber)) {
            viewModel.signUp(userEmail, userPassword);
        } else {
            for (EditText item : new EditText[]{
                    binding.etEmail, binding.etUsername, binding.etPassword, binding.etDateOfBirth, binding.etPhoneNumber
            }) {
                if (item != null) {
                    item.setError(item.getHint() + " is required");
                }
            }
        }
    }
}
