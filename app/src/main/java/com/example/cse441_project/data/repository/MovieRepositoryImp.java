package com.example.cse441_project.data.repository;

import android.util.Log;

import com.example.cse441_project.data.model.genre.GenreMovie;
import com.example.cse441_project.data.model.genre.GenresItem;
import com.example.cse441_project.data.model.movie.NowPlayingMovie;
import com.example.cse441_project.data.model.movie.ResultsItem;
import com.example.cse441_project.data.model.moviedetail.MovieDetail;
import com.example.cse441_project.data.model.movietrailer.MovieTrailer;
import com.example.cse441_project.data.model.movietrailer.MovieTrailerItem;
import com.example.cse441_project.data.resource.MovieApi;
import com.example.cse441_project.data.resource.Result;
import com.example.cse441_project.data.resource.RetrofitHelper;

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
            Response<GenreMovie> genreMovieResponse = movieApi.getGenres().execute();
            if (genreMovieResponse.isSuccessful() && genreMovieResponse.body() != null) {
                for (GenresItem genreMovie : genreMovieResponse.body().getGenres()) {
                    genreMap.put(genreMovie.getId(), genreMovie.getName());
                }
            } else {
                return new Result.Error<>(new Exception("Failed to fetch Genre"));
            }

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
        try {
            Response<GenreMovie> genreMovieResponse = movieApi.getGenres().execute();
            if (genreMovieResponse.isSuccessful() && genreMovieResponse.body() != null) {
                for (GenresItem genreMovie : genreMovieResponse.body().getGenres()) {
                    genreMap.put(genreMovie.getId(), genreMovie.getName());
                }
            } else {
                return new Result.Error<>(new Exception("Failed to fetch Genre"));
            }

            Response<NowPlayingMovie> movieResponse = movieApi.getUpComingMovie().execute();
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
    public Result<MovieDetail> getDetailMovie(int id) throws Exception {
        try {
            Response<MovieDetail> movieDetailResponse = movieApi.getDetailMovie(id).execute();
            if(movieDetailResponse.isSuccessful() && movieDetailResponse.body()!= null){
                MovieDetail movieDetail = movieDetailResponse.body();
                return new Result.Success(movieDetail);
            }else {
                return new Result.Error<>(new Exception("Failed to get movie details: " + movieDetailResponse.message()));
            }
        }catch (IOException e){
            return new Result.Error<>(e);
        }
    }

    @Override
    public Result<MovieTrailer> getTrailerMovie(int id) throws Exception {
        try {
            Response<MovieTrailer> movieTrailerResponse = movieApi.getVideoTrailer(id).execute();

            if (movieTrailerResponse.isSuccessful() && movieTrailerResponse.body() != null) {
                MovieTrailer movieTrailer = movieTrailerResponse.body();
                return new Result.Success<>(movieTrailer);
            } else {
                return new Result.Error<>(new Exception("Failed to fetch trailer: " + movieTrailerResponse.message()));
            }

        } catch (IOException e) {
            return new Result.Error<>(e);
        }
    }
}
