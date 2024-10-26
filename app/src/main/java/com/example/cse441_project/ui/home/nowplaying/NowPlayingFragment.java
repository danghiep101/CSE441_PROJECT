package com.example.cse441_project.ui.home.nowplaying;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cse441_project.R;
import com.example.cse441_project.data.model.movie.ResultsItem;
import com.example.cse441_project.data.repository.MovieRepository;
import com.example.cse441_project.data.repository.MovieRepositoryImp;
import com.example.cse441_project.databinding.FragmentNowPlayingBinding;
import com.example.cse441_project.ui.bookticket.ChooseDateAndTimeActivity;
import com.example.cse441_project.ui.home.moviedetail.MovieDetailActivity;

import java.util.ArrayList;


public class NowPlayingFragment extends Fragment implements MovieRecyclerViewAdapter.OnItemClickListener {
    private FragmentNowPlayingBinding binding;
    private MovieRecyclerViewAdapter moviesAdapter;
    private NowPlayingMovieViewModel viewModel;
    private MovieRepository movieRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movieRepository = new MovieRepositoryImp();
        NowPlayingViewModelFactory factory = new NowPlayingViewModelFactory(movieRepository);
        viewModel = new ViewModelProvider(this, factory).get(NowPlayingMovieViewModel.class);

        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        setupRecyclerView();
        observeViewModel();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        moviesAdapter = new MovieRecyclerViewAdapter(new ArrayList(), this);
        binding.recyclerViewMovie.setAdapter(moviesAdapter);
        binding.recyclerViewMovie.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        viewModel.loadMovies();

    }

    private void observeViewModel() {
        viewModel.movies.observe(getViewLifecycleOwner(), movieList -> {
            if (movieList != null) {
                Log.e("NowPlayingFragment", "Received movies: " + movieList.size());
                moviesAdapter.addMovies(movieList);
            } else {
                Log.e("NowPlayingFragment", "movieList is null");
            }
        });

        viewModel.error.observe(getViewLifecycleOwner(), exception -> {
            if (exception != null) {
                Toast.makeText(requireContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onItemClick(ResultsItem movie) {
        Log.d("NowPlayingFragment", "Movie ID: " + movie.getId());
        Intent intent = new Intent(requireContext(), MovieDetailActivity.class);
        intent.putExtra("MOVIE_ID", movie.getId());
        startActivity(intent);
    }
}