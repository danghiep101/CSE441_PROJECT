package com.example.admincse441project.ui.ticketmanagement.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admincse441project.R;
import com.example.admincse441project.databinding.FragmentTicketListBinding;
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

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new TicketAdapter(null, ticket -> {
            TicketEditFragment ticketEditFragment = new TicketEditFragment();

            Bundle bundle = new Bundle();
            bundle.putString("TICKET_ID", ticket.getId());

            ticketEditFragment.setArguments(bundle);
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

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadTickets();
    }
}