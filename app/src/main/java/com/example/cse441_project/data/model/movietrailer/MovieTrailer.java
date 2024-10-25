package com.example.cse441_project.data.model.movietrailer;

import java.util.List;

public class MovieTrailer{
	private int id;
	private List<MovieTrailerItem> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<MovieTrailerItem> results){
		this.results = results;
	}

	public List<MovieTrailerItem> getResults(){
		return results;
	}
}