package com.example.admincse441project.ui.ticketmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.movie.NowPlayingMovie;
import com.example.admincse441project.data.model.movie.ResultsItem;
import com.example.admincse441project.data.repository.MovieRepository;
import com.example.admincse441project.data.repository.MovieRepositoryImp;
import com.example.admincse441project.data.resource.Result;

import java.util.List;

public class NowPlayingMovieViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private MutableLiveData<List<ResultsItem>> _movies = new MutableLiveData<>();
    public LiveData<List<ResultsItem>> movies = _movies;

    private final MutableLiveData<Exception> _error = new MutableLiveData<>();
    public LiveData<Exception> error = _error;

    public NowPlayingMovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = new MovieRepositoryImp();
    }

    public void loadMovies() {
        new Thread(() -> {
            try {
                Result<NowPlayingMovie> result = movieRepository.getMovie();
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
