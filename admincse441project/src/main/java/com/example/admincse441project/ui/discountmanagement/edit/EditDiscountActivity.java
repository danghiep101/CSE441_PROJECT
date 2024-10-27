package com.example.admincse441project.ui.discountmanagement.edit;

import static com.example.admincse441project.utils.FirebaseUtils.deleteDiscount;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.databinding.ActivityEditDiscountBinding;

public class EditDiscountActivity extends AppCompatActivity {
    private ActivityEditDiscountBinding binding;
    private EditDiscountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDiscountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(EditDiscountViewModel.class);
        Intent intent = getIntent();
        String discountId = intent.getStringExtra("DISCOUNT_ID");

        fetchDiscount(discountId);
        setupValidation();
        onViewClickListeners();

    }

    private void setupValidation() {
        binding.edtEditDiscountQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable input) {
                validateIntegerInput(input.toString(), binding.edtEditDiscountQuantity, "Quantity");
            }
        });

        binding.edtEditDiscountValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable input) {
                validateIntegerInput(input.toString(), binding.edtEditDiscountValue, "Value");
            }
        });
    }

    private void validateIntegerInput(String input, EditText editText, String fieldName) {
        if (!input.matches("\\d+")) {
            editText.setError(fieldName + " must be a integer.");
        } else {
            editText.setError(null);
        }
    }


    private void onViewClickListeners() {
        Intent intent = getIntent();
        String discountId = intent.getStringExtra("DISCOUNT_ID");
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnSave.setOnClickListener(v -> saveDiscount(discountId));
        binding.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm delete")
                    .setMessage("Are you sure you want to delete this discount?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        deleteDiscount(discountId);
                        Toast.makeText(this, "Discount deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void saveDiscount(String discountId) {
        String name = binding.edtEditDiscountName.getText().toString();
        int value = Integer.parseInt(binding.edtEditDiscountValue.getText().toString());
        int quantity = Integer.parseInt(binding.edtEditDiscountQuantity.getText().toString());
        String description = binding.edtEditDiscountDescription.getText().toString();

        Discount discount = new Discount( discountId, name, value, quantity, description);
        viewModel.updateDiscount(discount, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Discount updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update discount", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDiscount(String discountId) {

        viewModel.getDiscount(discountId).observe(this, discount -> {
            binding.edtEditDiscountName.setText(discount.getName());
            binding.edtEditDiscountValue.setText(String.valueOf(discount.getValue()));
            binding.edtEditDiscountQuantity.setText(String.valueOf(discount.getQuantity()));
            binding.edtEditDiscountDescription.setText(discount.getDescription());
        });
    }
}
