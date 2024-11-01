package com.example.cse441_project.ui.auth.login;

import android.content.Intent;
import android.content.SharedPreferences; // Import SharedPreferences
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.R;
import com.example.cse441_project.databinding.ActivityLoginBinding;
import com.example.cse441_project.ui.auth.passwordreset.PasswordResetActivity;
import com.example.cse441_project.ui.auth.signup.SignUpActivity;
import com.example.cse441_project.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setContentView(binding.getRoot());
        onClickView();
        observeViewModel();
    }

    private void onClickView() {
        binding.btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            signIn();
        });
        binding.btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, PasswordResetActivity.class);
            startActivity(intent);
            finish();
        });
        binding.btnBackSignup.setOnClickListener(v -> {
            finish();
        });
    }

    private void signIn() {
        String userEmail = binding.etEmail.getText().toString().trim();
        String userPassword = binding.etPassword.getText().toString().trim();

        if (viewModel.notEmpty(userEmail, userPassword)) {
            viewModel.login(userEmail, userPassword);
        } else {
            for (EditText item : new EditText[]{binding.etEmail, binding.etPassword}) {
                item.setError(item.getHint() + " is required");
            }
        }
    }

    private void observeViewModel() {
        viewModel.loginStatus.observe(this, isSuccess -> {
            if (isSuccess) {

                String userId = viewModel.getUserId();
                Log.e("CheckoutActivity", "bookTicket: " +userId );

                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER_ID", userId);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Email or password is incorrect, please try again", Toast.LENGTH_LONG).show();
            }
        });
    }
}
