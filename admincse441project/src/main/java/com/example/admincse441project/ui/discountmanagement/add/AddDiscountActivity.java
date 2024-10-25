package com.example.admincse441project.ui.discountmanagement.add;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.admincse441project.R;
import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.databinding.ActivityAddDiscountBinding;


public class AddDiscountActivity extends AppCompatActivity {
    private ActivityAddDiscountBinding binding;
    private AddDiscountViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDiscountBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AddDiscountViewModel.class);
        setContentView(binding.getRoot());

        onClickView();
    }

    private void onClickView() {


        binding.btnAdd.setOnClickListener(v -> {
            String discountName = binding.edtDiscountName.getText().toString();
            String discountQuantityStr = binding.edtDiscountQuantity.getText().toString();
            String discountValueStr = binding.edtDiscountValue.getText().toString();
            String discountDescription = binding.edtDiscountDescription.getText().toString();


            int discountQuantity = Integer.parseInt(discountQuantityStr);
            int discountValue = Integer.parseInt(discountValueStr);
            Discount discount = new Discount(null, discountName, discountValue, discountQuantity, discountDescription);
            if (discountName.isEmpty() || discountQuantityStr.isEmpty() || discountValueStr.isEmpty() || discountDescription.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.addDiscount(discount).addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Successfully added discount", Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(e -> {
                Log.e("AddDiscountActivity", "Error adding discount", e);
                Toast.makeText(this, "Add discount failed", Toast.LENGTH_SHORT).show();
            });
        });
    }
}