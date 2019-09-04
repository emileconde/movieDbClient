package com.example.android.conde.com.cinephile.repositories;

import com.example.android.conde.com.cinephile.models.Profile;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.network.MovieApiClient;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class ProfileRepository {
    private static ProfileRepository sInstance;
    private MovieApiClient mMovieApiClient;

    public static ProfileRepository getInstance() {
        if (sInstance == null)
            sInstance = new ProfileRepository();
        return sInstance;
    }

    private ProfileRepository() {
        mMovieApiClient = MovieApiClient.getInstance();
    }


    public void queryProfileMovies(int personID, String apiKey) {
        mMovieApiClient.queryProfileMovies(personID, apiKey);
    }

    public void queryProfile(int personID, String apiKey) {
        mMovieApiClient.queryProfile(personID, apiKey);
    }


    public MutableLiveData<Profile> getProfile() {
        return mMovieApiClient.getProfile();
    }

    public MutableLiveData<List<ProfileMovie>> getProfileMovieList() {
        return mMovieApiClient.getProfileMovieList();
    }


}
