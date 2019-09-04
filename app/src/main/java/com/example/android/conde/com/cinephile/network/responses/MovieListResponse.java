package com.example.android.conde.com.cinephile.network.responses;

import com.example.android.conde.com.cinephile.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListResponse {

    @SerializedName("page")
    @Expose
    int page;

    @SerializedName("total_results")
    @Expose
    int totalResult;

    @SerializedName("total_pages")
    @Expose
    int totalPage;


    @SerializedName("results")
    @Expose
    private List<Movie> mMovies;

    public List<Movie> getMovies() {
        return mMovies;
    }
}
