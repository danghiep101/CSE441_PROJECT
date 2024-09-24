package com.example.cse441_project.ui.nowplaying;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.model.ModelResponse;
import com.example.cse441_project.data.model.ResultsItem;
import com.example.cse441_project.data.repository.MovieRepository;
import com.example.cse441_project.data.repository.MovieRepositoryImp;
import com.example.cse441_project.data.resource.Result;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class NowPlayingViewmodel extends ViewModel {
    private final MovieRepository movieRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public NowPlayingViewmodel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public NowPlayingViewmodel(){
        this.movieRepository = new MovieRepositoryImp();
    }

    private final MutableLiveData<List<ResultsItem>> _movies = new MutableLiveData<>();
    public LiveData<List<ResultsItem>> getMovies() {
        return _movies;
    }


    private final MutableLiveData<Exception> _error = new MutableLiveData<>();
    public LiveData<Exception> getError() {
        return _error;
    }

    public void loadMovies(){

        executor.execute(() -> {
            Log.d("NowPlayingViewmodel", "loadMovies called");
            try {
                Result result = movieRepository.getMovie();
                if(result instanceof Result.Success){
                    List<ResultsItem> movies = ((Result.Success<ModelResponse>) result).getData().getResults();
                    Log.e("NowPlayingViewmodel", "Movies loaded: " + movies.size());
                    _movies.postValue(movies);
                } else if (result instanceof Result.Error) {
                    Log.e("NowPlayingViewmodel", "Movies Error");
                    _movies.postValue(null);
                }
            } catch (Exception e) {
                _movies.postValue(null);
                _error.postValue(e);
            }
        });

    }



}
