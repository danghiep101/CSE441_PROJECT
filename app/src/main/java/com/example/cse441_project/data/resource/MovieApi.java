package com.example.cse441_project.data.resource;

import com.example.cse441_project.data.model.genre.GenreMovie;
import com.example.cse441_project.data.model.movie.NowPlayingMovie;
import com.example.cse441_project.data.model.moviedetail.MovieDetail;
import com.example.cse441_project.data.model.movietrailer.MovieTrailer;
import com.example.cse441_project.data.model.movietrailer.MovieTrailerItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApi {
    @GET("3/movie/now_playing?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<NowPlayingMovie> getMovies();

    @GET("3/genre/movie/list?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<GenreMovie> getGenres();

    @GET("3/movie/upcoming?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<NowPlayingMovie> getUpComingMovie();

    @GET("/3/movie/{id}?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<MovieDetail> getDetailMovie(@Path("id") int id);

    @GET("3/movie/{movieId}/videos?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<MovieTrailer> getVideoTrailer(@Path("movieId") int movieId);

}
