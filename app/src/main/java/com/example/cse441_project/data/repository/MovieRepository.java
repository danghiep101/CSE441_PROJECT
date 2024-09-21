package com.example.cse441_project.data.repository;

import com.example.cse441_project.data.model.ResultsItem;
import com.example.cse441_project.data.resource.Result;

public interface MovieRepository {
    Result<ResultsItem> getMovie() throws Exception;
}
