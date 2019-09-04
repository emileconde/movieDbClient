package com.example.android.conde.com.cinephile.repositories;

import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.network.MovieApiClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class SimilarMoviesFragmentRepository {
    private static  SimilarMoviesFragmentRepository sInstance;
    private MovieApiClient mMovieApiClient;
    public static SimilarMoviesFragmentRepository getInstance(){
        if(sInstance == null){
            sInstance = new SimilarMoviesFragmentRepository();
        }

        return sInstance;
    }

    private SimilarMoviesFragmentRepository(){
        mMovieApiClient = MovieApiClient.getInstance();
    }


    public MutableLiveData<List<Movie>> getSimilarMovies() {
        return mMovieApiClient.getSimilarMovies();
    }

    public void querySimilarMovies(int movieID, String apiKey) {
        mMovieApiClient.querySimilarMovies(movieID, apiKey);
    }



}
