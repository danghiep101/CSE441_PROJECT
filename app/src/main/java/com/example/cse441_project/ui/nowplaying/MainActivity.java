package com.example.cse441_project.ui.nowplaying;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.data.model.ResultsItem;
import com.example.cse441_project.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NowPlayingRecyclerViewAdapter moviesAdapter;
    private NowPlayingViewmodel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(NowPlayingViewmodel.class);

        setUpRecyclerView();
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getMovies().observe(this, new Observer<List<ResultsItem>>() {
            @Override
            public void onChanged(List<ResultsItem> resultsItems) {
                if(resultsItems != null){
                    moviesAdapter.addMovie(resultsItems);
                }
            }
        });

    }

    private void setUpRecyclerView() {
        moviesAdapter = new NowPlayingRecyclerViewAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(moviesAdapter);
    }
}