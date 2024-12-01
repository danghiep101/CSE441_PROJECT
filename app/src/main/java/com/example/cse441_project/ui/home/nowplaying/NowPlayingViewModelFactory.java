package com.example.cse441_project.ui.home.nowplaying;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.data.repository.MovieRepository;

public class NowPlayingViewModelFactory implements ViewModelProvider.Factory {
    private final MovieRepository movieRepository;

    public NowPlayingViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NowPlayingMovieViewModel.class)) {
            return (T) new NowPlayingMovieViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
