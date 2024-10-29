    package com.example.admincse441project.ui.showtimemaganement;

    import android.content.Intent;
    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;
    import androidx.recyclerview.widget.LinearLayoutManager;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import com.example.admincse441project.databinding.FragmentDiscountsListBinding;
    import com.example.admincse441project.databinding.FragmentShowTimeListBinding;


    import java.util.List;

    public class ShowTimeListFragment extends Fragment {
        private FragmentShowTimeListBinding binding;
        private ShowTimeViewModel viewModel;
        private ShowTimeAdapter adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            binding = FragmentShowTimeListBinding.inflate(inflater, container, false);
            viewModel = new ViewModelProvider(this).get(ShowTimeViewModel.class);

            setupRecyclerView();
            setupObservers();
            onViewClickListeners();
            return binding.getRoot();
        }

        private void setupRecyclerView() {
            adapter = new ShowTimeAdapter(null, discount -> {
                Intent intent = new Intent(getActivity(), EditShowtimeActivity.class);
                intent.putExtra("SHOWTIME_ID", discount.getId());
                startActivity(intent);
            });
            binding.rcvManagementList.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rcvManagementList.setAdapter(adapter);
        }

        private void setupObservers() {
            viewModel.showtimes.observe(getViewLifecycleOwner(), showTimes -> {
                adapter.setShowTimeList(showTimes);
            });
        }

        private void onViewClickListeners() {
            binding.imageButton2.setOnClickListener(view -> {
                Intent intent = new Intent(requireContext(), AddShowtimeActivity.class);
                startActivity(intent);
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            viewModel.loadShowTimes();
        }
    }