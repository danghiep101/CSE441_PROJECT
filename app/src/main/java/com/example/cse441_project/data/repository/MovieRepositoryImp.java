package com.example.cse441_project.data.repository;

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
    public Result<ResultsItem> getMovie() throws Exception {
        try {
            Response<ResultsItem> response = movieApi.getMovies().execute();
            if(response.isSuccessful() && response.body() != null){
                return new Result.Success<>(response.body());
            }else {
                return new Result.Error<>(new Exception(response.message()));
            }

        }catch (IOException e){
            return new Result.Error<>(e);
        }
    }
}
