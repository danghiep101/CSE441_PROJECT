package com.example.cse441_project.ui.bookticket.showscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.data.model.showtime.ShowTime;
import com.example.cse441_project.databinding.ActivityChooseDateAndTimeBinding;

import java.util.List;

public class ChooseDateAndTimeActivity extends AppCompatActivity {
    private ActivityChooseDateAndTimeBinding binding;
    private ChooseDateAndTimeViewModel viewModel;
    private ChooseDateAdapter dateAdapter;
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

        // Load dữ liệu từ Firestore
        viewModel.loadDateList("showtimes", String.valueOf(id));
        viewModel.loadShowtimeList("showtimes", String.valueOf(id));
    }

    private void setupRecyclerViewDate() {
        dateAdapter = new ChooseDateAdapter(date -> {
            // Xử lý sự kiện khi chọn một ngày
            Intent intent = getIntent();
            id = intent.getIntExtra("MOVIE_ID", -1);
            viewModel.loadShowtimeList("showtimes", String.valueOf(id));
        });
        binding.rcvTimeAndScreen.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvDate.setAdapter(dateAdapter);
    }

    private void setupRecyclerViewScreenTime() {
        timeAdapter = new ChooseScreenTimeAdapter(showTime -> {
            // Xử lý sự kiện khi chọn giờ chiếu
        });
        binding.rcvTimeAndScreen.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvTimeAndScreen.setAdapter(timeAdapter);
    }

    private void observeViewModel() {
        // Quan sát danh sách ngày
        viewModel.getDateList().observe(this, dates -> {
            dateAdapter.submitList(dates);
        });

        // Quan sát danh sách giờ chiếu
        viewModel.getShowTimeList().observe(this, showTimes -> {
            timeAdapter.submitList(showTimes);
        });
    }
}
