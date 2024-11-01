package com.example.admincse441project.ui.discountmanagement.showdiscount;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
        setupObservers();
        onViewClickListeners();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new DiscountAdapter(null, discount -> {
            Intent intent = new Intent(getActivity(), EditDiscountActivity.class);
            intent.putExtra("DISCOUNT_ID", discount.getId());
            startActivity(intent);
        });
        binding.recyclerViewDiscount.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewDiscount.setAdapter(adapter);
    }

    private void setupObservers() {
        viewModel.discounts.observe(getViewLifecycleOwner(), discounts -> {
            adapter.setDiscountList(discounts);
        });
    }

    private void onViewClickListeners() {
        binding.btnAddDiscount.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), AddDiscountActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadDiscounts();
    }
}