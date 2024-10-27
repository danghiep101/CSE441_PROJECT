package com.example.admincse441project.ui.ticketmanagement.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.admincse441project.R;
import com.example.admincse441project.ui.ticketmanagement.add.TicketAddFragment;

public class TicketListFragment extends Fragment {
    private ImageButton img_btn_add_ticket_fragment;


    public TicketListFragment() {
        // Required empty public constructor
    }

    public static TicketListFragment newInstance() {
        return new TicketListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_list, container, false);

        img_btn_add_ticket_fragment =  view.findViewById(R.id.img_btn_add_ticket_fragment);
        img_btn_add_ticket_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketAddFragment ticketAddFragment = new TicketAddFragment();

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView, ticketAddFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}