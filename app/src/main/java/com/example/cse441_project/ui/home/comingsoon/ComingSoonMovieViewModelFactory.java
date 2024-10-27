package com.example.cse441_project.ui.home.comingsoon;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.data.repository.MovieRepository;


public class ComingSoonMovieViewModelFactory implements ViewModelProvider.Factory {
    private final MovieRepository movieRepository;

    public ComingSoonMovieViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ComingSoonMovieViewModel.class)) {
            return (T) new ComingSoonMovieViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
