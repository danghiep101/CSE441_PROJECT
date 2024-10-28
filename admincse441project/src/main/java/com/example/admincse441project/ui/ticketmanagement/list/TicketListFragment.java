package com.example.admincse441project.ui.ticketmanagement.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admincse441project.R;
import com.example.admincse441project.databinding.FragmentTicketListBinding;
import com.example.admincse441project.ui.ticketmanagement.add.TicketAddFragment;
import com.example.admincse441project.ui.ticketmanagement.edit.TicketEditFragment;

public class TicketListFragment extends Fragment {
    private FragmentTicketListBinding binding;
    private TicketViewModel viewModel;
    private TicketAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTicketListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TicketViewModel.class);

        setupRecyclerView();
        setupObservers();
        onViewClickListeners();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new TicketAdapter(null, ticket -> {
            TicketEditFragment ticketEditFragment = new TicketEditFragment();

            Bundle bundle = new Bundle();
            bundle.putString("TICKET_ID", ticket.getId());

            // Gán Bundle vào Fragment
            ticketEditFragment.setArguments(bundle);

            // Chuyển sang Fragment khác
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, ticketEditFragment)
                    .addToBackStack(null)  // Thêm vào backstack nếu muốn quay lại
                    .commit();
        });
        binding.rcvTicketList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvTicketList.setAdapter(adapter);
    }

    private void setupObservers() {
        viewModel.tickets.observe(getViewLifecycleOwner(), tickets -> {
            adapter.setTicketList(tickets);
        });
    }

    private void onViewClickListeners() {
        binding.btnSwitchAddTicket.setOnClickListener(view -> {
            TicketAddFragment ticketAddFragment = new TicketAddFragment();

            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, ticketAddFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadTickets();
    }
}