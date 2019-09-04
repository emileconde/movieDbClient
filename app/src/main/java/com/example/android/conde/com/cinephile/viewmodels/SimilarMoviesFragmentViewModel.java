package com.example.android.conde.com.cinephile.viewmodels;

import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.repositories.SimilarMoviesFragmentRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SimilarMoviesFragmentViewModel extends ViewModel {

    private SimilarMoviesFragmentRepository mRepository;

    public SimilarMoviesFragmentViewModel(){
        mRepository = SimilarMoviesFragmentRepository.getInstance();
    }


    public MutableLiveData<List<Movie>> getSimilarMovies() {
        return mRepository.getSimilarMovies();
    }

    public void querySimilarMovies(int movieID, String apiKey) {
        mRepository.querySimilarMovies(movieID, apiKey);
    }


}
