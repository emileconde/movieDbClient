package com.example.android.conde.com.cinephile.network.responses;

import com.example.android.conde.com.cinephile.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarMoviesResponse {
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<Movie> similarMovies;

    public List<Movie> getSimilarMovies() {
        return similarMovies;
    }
}
