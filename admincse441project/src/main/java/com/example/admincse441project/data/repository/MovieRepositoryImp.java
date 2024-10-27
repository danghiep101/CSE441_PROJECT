package com.example.admincse441project.data.repository;

import android.util.Log;

import com.example.admincse441project.data.model.movie.NowPlayingMovie;
import com.example.admincse441project.data.model.movie.ResultsItem;
import com.example.admincse441project.data.resource.MovieApi;
import com.example.admincse441project.data.resource.Result;
import com.example.admincse441project.data.resource.RetrofitHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class MovieRepositoryImp implements MovieRepository{

    private final MovieApi movieApi;
    private Map<Integer, String> genreMap = new HashMap<>();

    public MovieRepositoryImp(){
        movieApi = RetrofitHelper.getInstance().create(MovieApi.class);
    }

    @Override
    public Result<NowPlayingMovie> getMovie() throws Exception {
        try {
            Response<NowPlayingMovie> movieResponse = movieApi.getMovies().execute();
            if (movieResponse.isSuccessful() && movieResponse.body() != null) {
                List<ResultsItem> movieItems = movieResponse.body().getResults();
                if (movieItems != null) {
                    for (ResultsItem movie : movieItems) {
                        List<Integer> genreIds = movie.getGenreIds();
                        if (genreIds != null) {
                            List<String> genreNames = new ArrayList<>();
                            for (Integer genreId : genreIds) {
                                String genreName = genreMap.get(genreId);
                                if (genreName != null) {
                                    genreNames.add(genreName);
                                }
                            }
                            if (genreNames.size() > 3) {
                                genreNames = genreNames.subList(0, 3);
                            }
                            movie.setGenreNames(genreNames);
                        } else {
                            Log.e("MovieRepositoryImp", "Genre IDs are null for movie: " + movie.getTitle());
                        }
                    }
                } else {
                    Log.e("MovieRepositoryImp", "Movie results are null");
                }

                Log.d("MovieRepositoryImp", "Success: " + movieResponse.body().getResults());
                return new Result.Success<>(movieResponse.body());
            } else {
                Log.e("MovieRepositoryImp", "Error fetching movies: " + movieResponse.message());
                return new Result.Error<>(new Exception(movieResponse.message()));
            }
        } catch (IOException e) {
            return new Result.Error<>(e);
        }
    }

    @Override
    public Result<NowPlayingMovie> getUpComingMovie() throws Exception {
        return null;
    }
}
