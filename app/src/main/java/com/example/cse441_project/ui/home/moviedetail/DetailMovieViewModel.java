package com.example.cse441_project.ui.home.moviedetail;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.model.moviedetail.MovieDetail;
import com.example.cse441_project.data.model.movietrailer.MovieTrailer;
import com.example.cse441_project.data.model.movietrailer.MovieTrailerItem;
import com.example.cse441_project.data.repository.MovieRepository;
import com.example.cse441_project.data.repository.MovieRepositoryImp;
import com.example.cse441_project.data.resource.Result;

import java.util.List;

public class DetailMovieViewModel extends ViewModel {
    private MovieRepository movieRepository;

    private MutableLiveData<MovieDetail> _movieDetail = new MutableLiveData<>();
    public LiveData<MovieDetail> movieDetail = _movieDetail;

    private MutableLiveData<List<MovieTrailerItem>> _movieTrailer = new MutableLiveData<>();
    public LiveData<List<MovieTrailerItem>> movieTrailer = _movieTrailer;

    private MutableLiveData<Exception> _error = new MutableLiveData<>();
    public LiveData<Exception> error = _error;

    public DetailMovieViewModel() {
        this.movieRepository = new MovieRepositoryImp();
    }

    public void loadMovie(int id) {
        new Thread(() -> {
            try {
                Result<MovieDetail> detailMovie = movieRepository.getDetailMovie(id);
                if(detailMovie instanceof Result.Success){
                    _movieDetail.postValue(((Result.Success<MovieDetail>) detailMovie).getData());;
                } else if (detailMovie instanceof  Result.Error) {
                    _movieDetail.postValue(null);
                    _error.postValue(((Result.Error< MovieDetail>) detailMovie).getException());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }).start();

        new Thread(() -> {
            try {
                Result<MovieTrailer> movieTrailerResult = movieRepository.getTrailerMovie(id);
                if (movieTrailerResult instanceof Result.Success) {
                    List<MovieTrailerItem> trailers = ((Result.Success<MovieTrailer>) movieTrailerResult).getData().getResults();
                    _movieTrailer.postValue(trailers);
                } else if (movieTrailerResult instanceof Result.Error) {
                    _movieTrailer.postValue(null);
                    _error.postValue(((Result.Error<MovieTrailer>) movieTrailerResult).getException());
                }
            } catch (Exception e) {
                _error.postValue(e);
            }
        }).start();
    }
}
