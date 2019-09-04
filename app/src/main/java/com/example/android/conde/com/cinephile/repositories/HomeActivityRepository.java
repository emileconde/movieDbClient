package com.example.android.conde.com.cinephile.repositories;

import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.network.MovieApiClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class HomeActivityRepository {
    private static HomeActivityRepository mInstance;
    private MovieApiClient mMovieApiClient;
    private int mPageNumber;
    private String mKey;
    private boolean mIncludeAdult;
    private boolean mIncludeVideo;
    private String mSortBy;

    public static HomeActivityRepository getInstance() {
        if (mInstance == null) {
            mInstance = new HomeActivityRepository();
        }
        return mInstance;
    }

    private HomeActivityRepository() {
        mMovieApiClient = MovieApiClient.getInstance();
    }


    public MutableLiveData<List<Movie>> getMovieList() {
        return mMovieApiClient.getMovieList();
    }

    public MutableLiveData<List<Movie>> getNowPlayingList() {
        return mMovieApiClient.getNowPlayingList();
    }

    public void queryMovies(String key, String sortBy, boolean includeAdult
            , boolean includeVideo, int pageNumber) {
        mPageNumber = pageNumber;
        mKey = key;
        mIncludeAdult = includeAdult;
        mIncludeVideo = includeVideo;
        mSortBy = sortBy;
        mMovieApiClient.queryMovies(key, sortBy, includeAdult, includeVideo, pageNumber);
    }

    public void queryNowPlayingMovies(String key) {
        mMovieApiClient.queryNowPlayingMovies(key);
    }

    public MutableLiveData<List<Movie>> getHighestRatedList() {
        return mMovieApiClient.getHighestRatedList();
    }

    public void searchNextMoviePage() {
        queryMovies(mKey, mSortBy, mIncludeAdult, mIncludeVideo, mPageNumber + 1);
    }


    public void searchMovies(String apiKey, String query, boolean includeAdult) {
        mMovieApiClient.searchMovies(apiKey, query, includeAdult);
    }


    public MutableLiveData<List<Movie>> getSearchedMoviesList() {
        return mMovieApiClient.getSearchedMoviesList();
    }


}
