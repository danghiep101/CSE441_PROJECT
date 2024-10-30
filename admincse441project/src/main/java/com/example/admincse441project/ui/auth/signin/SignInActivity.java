package com.example.admincse441project.ui.auth.signin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.admincse441project.R;
import com.example.admincse441project.databinding.ActivitySignInBinding;
import com.example.admincse441project.databinding.ActivitySignUpBinding;
import com.example.admincse441project.ui.auth.forgotpassword.ForgotPasswordActivity;
import com.example.admincse441project.ui.auth.signup.SignUpActivity;
import com.example.admincse441project.ui.home.MainActivity;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private SignInViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        onClickView();
        observeViewModel();
    }

    private void onClickView() {
        binding.txtSignUp.setOnClickListener( v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            signIn();
        });
        binding.txtForgotpassword.setOnClickListener(v->{
            Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void signIn() {
        String adminEmail = binding.etEmail.getText().toString().trim();
        String adminPassword = binding.etPassword.getText().toString().trim();

        if(viewModel.notEmpty(adminEmail, adminPassword)){
            viewModel.login(adminEmail, adminPassword);
        }else {
            for (EditText item: new EditText[]{binding.etEmail, binding.etPassword}) {
                item.setError(item.getHint() + " is required");
            }
        }

    }

    private void observeViewModel() {
        viewModel.loginStatus.observe(this, isSuccess -> {
            if (isSuccess){
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}