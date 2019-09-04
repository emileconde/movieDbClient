package com.example.android.conde.com.cinephile.repositories;

import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Review;
import com.example.android.conde.com.cinephile.models.Trailer;
import com.example.android.conde.com.cinephile.network.MovieApiClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class MovieDetailRepository {
    private static final String TAG = "MovieDetailRepository";
    private static MovieDetailRepository sInstance;
    private MovieApiClient mMovieApiClient;

    public static MovieDetailRepository getInstance() {
        if (sInstance == null) {
            sInstance = new MovieDetailRepository();
        }
        return sInstance;
    }

    private MovieDetailRepository() {
        mMovieApiClient = MovieApiClient.getInstance();
    }


    public MutableLiveData<List<Review>> getReviewList() {
        return mMovieApiClient.getReviewList();
    }

    public void queryReviews(int movieId, String key) {
        mMovieApiClient.queryReviews(movieId, key);
    }


    public MutableLiveData<List<Trailer>> getTrailerList() {
        return mMovieApiClient.getTrailerList();
    }

    public void queryTrailers(int movieID, String key) {
        mMovieApiClient.queryTrailers(movieID, key);
    }


    public MutableLiveData<List<Cast>> getCastList() {
        return mMovieApiClient.getCastList();
    }

    public void queryCast(int movieId, String key) {
        mMovieApiClient.queryCast(movieId, key);
    }


}
