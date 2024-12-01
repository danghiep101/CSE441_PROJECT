package com.example.admincse441project.data.repository;

import com.example.admincse441project.data.model.movie.NowPlayingMovie;
import com.example.admincse441project.data.resource.Result;

public interface MovieRepository {
    Result<NowPlayingMovie> getMovie() throws Exception;
    Result<NowPlayingMovie> getUpComingMovie() throws Exception;
}