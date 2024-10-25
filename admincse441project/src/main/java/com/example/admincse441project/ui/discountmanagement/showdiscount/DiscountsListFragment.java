package com.example.admincse441project.ui.discountmanagement.showdiscount;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.databinding.FragmentDiscountsListBinding;
import com.example.admincse441project.ui.discountmanagement.add.AddDiscountActivity;
import com.example.admincse441project.ui.discountmanagement.edit.EditDiscountActivity;

import java.util.List;


public class DiscountsListFragment extends Fragment {
    private FragmentDiscountsListBinding binding;
    private DiscountViewModel viewModel;
    private DiscountAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscountsListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(DiscountViewModel.class);
        setupRecyclerView();
        onClickView();
        observeDiscounts();
        return binding.getRoot();


    }

    private void setupRecyclerView() {
        adapter = new DiscountAdapter(null, discount -> {
            Intent intent = new Intent(getActivity(), EditDiscountActivity.class);
            intent.putExtra("DISCOUNT_ID", discount.getId());
            intent.putExtra("DISCOUNT_NAME", discount.getName());
            intent.putExtra("DISCOUNT_VALUE", discount.getValue());
            intent.putExtra("DISCOUNT_QUANTITY", discount.getQuantity());
            intent.putExtra("DISCOUNT_DESCRIPTION", discount.getDescription());
            startActivity(intent);
            //todo: fetch discount detail to edit
        });
        binding.recyclerViewDiscount.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewDiscount.setAdapter(adapter);
    }

    private void observeDiscounts() {
        viewModel.getDiscounts().observe(getViewLifecycleOwner(), new Observer<List<Discount>>() {
            @Override
            public void onChanged(List<Discount> discounts) {
                adapter.setDiscountList(discounts);
            }
        });
    }
    private void onClickView() {
        binding.btnAddDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddDiscountActivity.class);
                startActivity(intent);
            }
        });
    }
}