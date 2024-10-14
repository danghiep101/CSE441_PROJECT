package com.example.cse441_project.data.repository;

import android.telecom.Call;

import com.example.cse441_project.data.model.GenreMovie;
import com.example.cse441_project.data.model.NowPlayingMovie;
import com.example.cse441_project.data.resource.Result;

public interface MovieRepository {
    Result<NowPlayingMovie> getMovie() throws Exception;

}
