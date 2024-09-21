package com.example.cse441_project.data.resource;

import com.example.cse441_project.data.model.ResultsItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("3/movie/now_playing")
    Call<ResultsItem> getMovies();

}
