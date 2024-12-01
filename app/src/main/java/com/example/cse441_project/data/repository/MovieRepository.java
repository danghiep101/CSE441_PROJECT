package com.example.cse441_project.data.repository;

import com.example.cse441_project.data.model.movie.NowPlayingMovie;
import com.example.cse441_project.data.model.moviedetail.MovieDetail;
import com.example.cse441_project.data.model.movietrailer.MovieTrailer;
import com.example.cse441_project.data.model.movietrailer.MovieTrailerItem;
import com.example.cse441_project.data.resource.Result;

public interface MovieRepository {
    Result<NowPlayingMovie> getMovie() throws Exception;
    Result<NowPlayingMovie> getUpComingMovie() throws Exception;
    Result<MovieDetail> getDetailMovie(int id) throws  Exception;
    Result<MovieTrailer> getTrailerMovie(int id) throws Exception;
}
