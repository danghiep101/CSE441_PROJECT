package com.example.cse441_project.ui.home.nowplaying;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.model.NowPlayingMovie;
import com.example.cse441_project.data.model.ResultsItem;
import com.example.cse441_project.data.repository.MovieRepository;
import com.example.cse441_project.data.repository.MovieRepositoryImp;
import com.example.cse441_project.data.resource.Result;

import java.util.List;

import kotlinx.coroutines.CoroutineScope;

public class NowPlayingMovieViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private MutableLiveData<List<ResultsItem>> _movies = new MutableLiveData<>();
    public LiveData<List<ResultsItem>> movies = _movies;

    private final MutableLiveData<Exception> _error = new MutableLiveData<>();
    public LiveData<Exception> error = _error;

    public NowPlayingMovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = new MovieRepositoryImp();
    }

    void loadMovies() {
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
