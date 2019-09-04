package com.example.android.conde.com.cinephile.viewmodels;

import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.repositories.HomeActivityRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeActivityViewModel extends ViewModel {
    private HomeActivityRepository mRepository;

    public HomeActivityViewModel() {
        mRepository = HomeActivityRepository.getInstance();
    }


    public MutableLiveData<List<Movie>> getMovieList() {
        return mRepository.getMovieList();
    }

    public MutableLiveData<List<Movie>> getNowPlayingList() {
        return mRepository.getNowPlayingList();
    }

    public void queryMovies(String key, String sortBy, boolean includeAdult
            , boolean includeVideo, int pageNumber) {
        mRepository.queryMovies(key, sortBy, includeAdult, includeVideo, pageNumber);
    }

    public void queryNowPlayingMovies(String key) {
        mRepository.queryNowPlayingMovies(key);
    }

    public MutableLiveData<List<Movie>> getHighestRatedList() {
        return mRepository.getHighestRatedList();
    }

    public void searchNextMoviePage() {
        mRepository.searchNextMoviePage();
    }


    public void searchMovies(String apiKey, String query, boolean includeAdult) {
        mRepository.searchMovies(apiKey, query, includeAdult);
    }


    public MutableLiveData<List<Movie>> getSearchedMoviesList() {
        return mRepository.getSearchedMoviesList();
    }


}
