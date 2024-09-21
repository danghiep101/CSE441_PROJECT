package com.example.cse441_project.data.resource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper{
    private static final String  BASE_URL = "https://api.themoviedb.org/";

    public static Retrofit getInstance(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
