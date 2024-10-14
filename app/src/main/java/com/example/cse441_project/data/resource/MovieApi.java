package com.example.cse441_project.data.resource;

import com.example.cse441_project.data.model.GenreMovie;
import com.example.cse441_project.data.model.NowPlayingMovie;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("3/movie/now_playing?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<NowPlayingMovie> getMovies();

    @GET("3/genre/movie/list?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<GenreMovie> getGenres();
}
