package com.example.cse441_project.ui.bookticket.showscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.databinding.ActivityChooseDateAndTimeBinding;
import com.example.cse441_project.ui.home.moviedetail.DetailMovieViewModel;

public class ChooseDateAndTimeActivity extends AppCompatActivity {
    private ActivityChooseDateAndTimeBinding binding;
    private ChooseDateAndTimeViewModel viewModel;
    private ChooseDateAdapter   dateAdapter;
    private ChooseScreenTimeAdapter timeAdapter;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseDateAndTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ChooseDateAndTimeViewModel.class);
        Intent intent = getIntent();
        id = intent.getIntExtra("MOVIE_ID", -1);

        setupRecyclerViewDate();
        setupRecyclerViewScreenTime();
        observeViewModel();
        viewModel.fetchShowtimeData();



    }

    private void setupRecyclerViewDate() {
        dateAdapter = new ChooseDateAdapter();
        binding.rcvDate.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvDate.setAdapter(dateAdapter);
    }

    private void setupRecyclerViewScreenTime() {
        timeAdapter = new ChooseScreenTimeAdapter();
        binding.rcvTimeAndScreen.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvTimeAndScreen.setAdapter(timeAdapter);
    }

    private void observeViewModel() {
        viewModel.getShowScreenList().observe(this, screens -> {
            // Update adapters with data
            dateAdapter.setShowScreenList(screens);
            timeAdapter.setShowScreenList(screens);
        });
    }
}