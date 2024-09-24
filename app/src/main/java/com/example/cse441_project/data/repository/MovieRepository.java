package com.example.cse441_project.data.repository;

import com.example.cse441_project.data.model.ModelResponse;
import com.example.cse441_project.data.resource.Result;

public interface MovieRepository {
    Result<ModelResponse> getMovie() throws Exception;
}
