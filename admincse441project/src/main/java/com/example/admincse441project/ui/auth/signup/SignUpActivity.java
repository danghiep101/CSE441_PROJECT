package com.example.admincse441project.ui.auth.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.admincse441project.R;
import com.example.admincse441project.databinding.ActivitySignUpBinding;
import com.example.admincse441project.ui.auth.signin.SignInActivity;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        setContentView(binding.getRoot());

        onClickView();
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.signUpStatus.observe(this, isSuccess -> {
            if (isSuccess) {
                String adminEmail = binding.etEmail.getText().toString().trim();
                String adminName = binding.etFullname.getText().toString().trim();
                String phoneNumber = binding.etPhoneNumber.getText().toString().trim();
                String dateOfBirth = binding.etDateOfBirth.getText().toString().trim();
                viewModel.saveUserDetail(adminName, adminEmail, dateOfBirth, phoneNumber);
            } else {
                Toast.makeText(this, "Cannot Sign Up", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.saveAdminDetailStatus.observe(this, isSuccess -> {
            if (isSuccess) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(this, "Sign Up Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Sign Up failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onClickView() {
        binding.txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
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
        String userName = binding.etFullname.getText().toString().trim();
        String dateOfBirth = binding.etDateOfBirth.getText().toString().trim();
        String phoneNumber = binding.etPhoneNumber.getText().toString().trim();

        if (viewModel.notEmpty(userEmail, userName, userPassword, dateOfBirth, phoneNumber)) {
            viewModel.signUp(userEmail, userPassword);
        } else {
            for (EditText item : new EditText[]{
                    binding.etEmail, binding.etFullname, binding.etPassword, binding.etDateOfBirth, binding.etPhoneNumber
            }) {
                if (item != null) {
                    item.setError(item.getHint() + " is required");
                }
            }
        }
    }

}