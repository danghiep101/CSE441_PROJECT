package com.example.cse441_project.ui.home.comingsoon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cse441_project.R;
import com.example.cse441_project.data.model.ResultsItem;
import com.example.cse441_project.data.repository.MovieRepository;
import com.example.cse441_project.data.repository.MovieRepositoryImp;
import com.example.cse441_project.databinding.FragmentComingSoonBinding;
import com.example.cse441_project.databinding.FragmentNowPlayingBinding;
import com.example.cse441_project.ui.home.moviedetail.MovieDetailActivity;
import com.example.cse441_project.ui.home.nowplaying.MovieRecyclerViewAdapter;
import com.example.cse441_project.ui.home.nowplaying.NowPlayingMovieViewModel;
import com.example.cse441_project.ui.home.nowplaying.NowPlayingViewModelFactory;

import java.util.ArrayList;


public class ComingSoonFragment extends Fragment implements ComingSoonRecyclerViewAdapter.OnItemClickListener {
    private ComingSoonRecyclerViewAdapter moviesAdapter;
    private ComingSoonMovieViewModel viewModel;
    private MovieRepository movieRepository;
    private FragmentComingSoonBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movieRepository = new MovieRepositoryImp();
        ComingSoonMovieViewModelFactory factory = new ComingSoonMovieViewModelFactory(movieRepository);
        viewModel = new ViewModelProvider(this, factory).get(ComingSoonMovieViewModel.class);
        binding = FragmentComingSoonBinding.inflate(inflater, container, false);
        setupRecyclerView();
        observeViewModel();
        return binding.getRoot();
    }

    private void observeViewModel() {
        viewModel.movies.observe(getViewLifecycleOwner(), movieList -> {
            if (movieList != null) {
                Log.e("ComingSoonFragment", "Received movies: " + movieList.size());
                moviesAdapter.addMovies(movieList);
            } else {
                Log.e("ComingSoonFragment", "movieList is null");
            }
        });

        viewModel.error.observe(getViewLifecycleOwner(), exception -> {
            if (exception != null) {
                Toast.makeText(requireContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRecyclerView() {
        moviesAdapter = new ComingSoonRecyclerViewAdapter(new ArrayList(), this);
        binding.recyclerView.setAdapter(moviesAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        viewModel.loadMovies();
        
    }

    @Override
    public void onItemClick(ResultsItem movie) {
        Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
        startActivity(intent);
    }
}