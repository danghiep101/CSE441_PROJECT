package com.example.admincse441project.data.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowPlayingMovie {
	private int page;
	private int totalPages;
	@SerializedName("results") private List<ResultsItem> results;
	private int totalResults;

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}
}