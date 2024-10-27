package com.example.admincse441project.data.resource;

import com.example.admincse441project.data.model.movie.NowPlayingMovie;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("3/movie/now_playing?api_key=ed5fb4eff85f0e634388e9c3f7a86f5a")
    Call<NowPlayingMovie> getMovies();
}
