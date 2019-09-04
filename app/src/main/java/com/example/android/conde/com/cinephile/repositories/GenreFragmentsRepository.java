package com.example.android.conde.com.cinephile.repositories;

import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.network.MovieApiClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class GenreFragmentsRepository {
    private static GenreFragmentsRepository mInstance;
    private MovieApiClient mMovieApiClient;
    private int pageNumber;

    public static GenreFragmentsRepository getInstance(){
        if(mInstance == null){
            mInstance = new GenreFragmentsRepository();
        }
        return mInstance;
    }

    private GenreFragmentsRepository() {
        mMovieApiClient = MovieApiClient.getInstance();
    }


    public MutableLiveData<List<Movie>> getActionMovieGenreList() {
        return mMovieApiClient.getActionMovieGenreList();
    }

    public MutableLiveData<List<Movie>> getAdventureMovieGenreList() {
        return mMovieApiClient.getAdventureMovieGenreList();
    }

    public MutableLiveData<List<Movie>> getAnimationMovieGenreList() {
        return mMovieApiClient.getAnimationMovieGenreList();
    }

    public MutableLiveData<List<Movie>> getComedyMovieGenreList() {
        return mMovieApiClient.getComedyMovieGenreList();
    }

    public MutableLiveData<List<Movie>> getDocumentaryMovieGenreList() {
        return mMovieApiClient.getDocumentaryMovieGenreList();
    }

    public void queryMoviesByGenre(String key, String language, String region, String sortBy,
                                   boolean includeAdult, boolean includeVideo, int pageNumber, String withGenre){
        mMovieApiClient.queryMoviesByGenre(key, language, region, sortBy, includeAdult, includeVideo, pageNumber, withGenre);
    }
}
