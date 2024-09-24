package com.example.cse441_project.data.repository;

import android.util.Log;

import com.example.cse441_project.data.model.ModelResponse;
import com.example.cse441_project.data.model.ResultsItem;
import com.example.cse441_project.data.resource.MovieApi;
import com.example.cse441_project.data.resource.Result;
import com.example.cse441_project.data.resource.RetrofitHelper;

import java.io.IOException;

import retrofit2.Response;

public class MovieRepositoryImp implements MovieRepository{

    private final MovieApi movieApi;

    public MovieRepositoryImp(){
        movieApi = RetrofitHelper.getInstance().create(MovieApi.class);
    }

    @Override
    public Result<ModelResponse> getMovie() throws Exception {
        try {
            Response<ModelResponse> response = movieApi.getMovies().execute();
            if(response.isSuccessful() && response.body() != null){
                Log.d("MovieRepositoryImp", "Success" + response.body().getResults());
                return new Result.Success<>(response.body());
            }else {
                Log.d("MovieRepositoryImp", "Error" + response.body());
                return new Result.Error<>(new Exception(response.message()));

            }

        }catch (IOException e){
            return new Result.Error<>(e);
        }
    }
}
