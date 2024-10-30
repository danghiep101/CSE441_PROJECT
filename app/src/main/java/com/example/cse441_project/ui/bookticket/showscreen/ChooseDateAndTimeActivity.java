package com.example.cse441_project.ui.bookticket.showscreen;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.data.model.showtime.ShowTime;
import com.example.cse441_project.databinding.ActivityChooseDateAndTimeBinding;
import com.example.cse441_project.ui.bookticket.chooseseat.ChooseSeatActivity;
import com.example.cse441_project.utils.FirebaseUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseDateAndTimeActivity extends AppCompatActivity {

    private ActivityChooseDateAndTimeBinding binding;
    private ChooseDateAndTimeViewModel viewModel;
    private ChooseDateAdapter chooseDateAdapter;
    private ChooseScreenTimeAdapter chooseScreenTimeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseDateAndTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ChooseDateAndTimeViewModel.class);
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("MOVIE_ID", -1);
        binding.txtNameMovie.setText(intent.getStringExtra("MOVIE_NAME"));
        if (movieId != -1) {
            FirebaseUtils.getShowtimeIdmovie(String.valueOf(movieId)).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                    viewModel.loadShowtime(String.valueOf(movieId));
                } else {
                    Toast.makeText(this, "Movie is not available", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Invalid Movie ID", Toast.LENGTH_SHORT).show();
        }

        setupRecyclerViews();
        observeViewModel();
        onClickView();
    }

    private void onClickView() {
        binding.imgPoster.setOnClickListener(v -> {
            finish();
        });
    }

    private void setupRecyclerViews() {
        chooseDateAdapter = new ChooseDateAdapter(this::onDateClick);
        binding.rcvDate.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvDate.setAdapter(chooseDateAdapter);

        chooseScreenTimeAdapter = new ChooseScreenTimeAdapter(this::onShowTimeClick);
        binding.rcvTimeAndScreen.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcvTimeAndScreen.setAdapter(chooseScreenTimeAdapter);
    }

    private void observeViewModel() {
        viewModel.showTimeList.observe(this, showTimeList -> {
            Log.d("ChooseDateAndTimeActivity", "Showtime List Observed: " + showTimeList);
            chooseScreenTimeAdapter.submitList(showTimeList);
            chooseDateAdapter.submitList(showTimeList);
        });
    }


    private void onDateClick(String date) {

        Toast.makeText(this, "Ngày đã chọn: " + date, Toast.LENGTH_SHORT).show();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date selectedDate;
        try {
            selectedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        if (viewModel.showTimeList.getValue() != null) {
            List<ShowTime> filteredShowTimes = new ArrayList<>();
            for (ShowTime showTime : viewModel.showTimeList.getValue()) {
                Log.d("FilteringShowTimes", "ShowTime Date: " + showTime.getDate());
                try {
                    Date showTimeDate = dateFormat.parse(showTime.getDate());
                    if (showTimeDate != null && showTimeDate.equals(selectedDate)) {

                        filteredShowTimes.add(showTime);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Log.d("FilteredShowTimes", "Filtered ShowTimes: " + filteredShowTimes);
            chooseScreenTimeAdapter.submitList(filteredShowTimes);
        } else {
            Log.d("ShowTimeList", "showTimeList is null or empty");
        }
    }

    private void onShowTimeClick(ShowTime showTime) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure want to choose this showtime? " + showTime.getStartTime() +
                " - " + showTime.getEndTime() + " \nday " + showTime.getDate() + " \n " + showTime.getNameCinema());
        builder.setPositiveButton("Ok", (dialog, which) -> {
            Intent intent = new Intent(this, ChooseSeatActivity.class);
            intent.putExtra("SHOWTIME_ID", showTime.getId());
            intent.putExtra("SHOWTIME_AVAILABLE_SEAT", showTime.getAvailableSeat());

            startActivity(intent);
            setResult(RESULT_OK, intent);

            finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}