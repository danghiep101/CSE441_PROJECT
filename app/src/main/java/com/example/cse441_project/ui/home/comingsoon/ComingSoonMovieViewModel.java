package com.example.cse441_project.ui.home.comingsoon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.model.movie.NowPlayingMovie;
import com.example.cse441_project.data.model.movie.ResultsItem;
import com.example.cse441_project.data.repository.MovieRepository;
import com.example.cse441_project.data.repository.MovieRepositoryImp;
import com.example.cse441_project.data.resource.Result;

import java.util.List;

public class ComingSoonMovieViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private MutableLiveData<List<ResultsItem>> _movies = new MutableLiveData<>();
    public LiveData<List<ResultsItem>> movies = _movies;

    private final MutableLiveData<Exception> _error = new MutableLiveData<>();
    public LiveData<Exception> error = _error;

    public ComingSoonMovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = new MovieRepositoryImp();
    }

    void loadMovies() {
        new Thread(() -> {
            try {
                Result<NowPlayingMovie> result = movieRepository.getUpComingMovie();
                if (result instanceof Result.Success) {
                    _movies.postValue(((Result.Success<NowPlayingMovie>) result).getData().getResults());
                } else if (result instanceof Result.Error) {
                    _movies.postValue(null);
                    _error.postValue(((Result.Error<NowPlayingMovie>) result).getException());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
