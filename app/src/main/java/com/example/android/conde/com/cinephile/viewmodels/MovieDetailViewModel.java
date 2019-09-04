package com.example.android.conde.com.cinephile.viewmodels;

import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Review;
import com.example.android.conde.com.cinephile.models.Trailer;
import com.example.android.conde.com.cinephile.repositories.MovieDetailRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailViewModel extends ViewModel {
    private static final String TAG = "MovieDetailViewModel";
    private MovieDetailRepository mRepository;

    public MovieDetailViewModel(){
        mRepository = MovieDetailRepository.getInstance();
    }

    public MutableLiveData<List<Review>> getReviewList() {
        return mRepository.getReviewList();
    }

    public void queryReviews(int movieId, String key){
        mRepository.queryReviews(movieId, key);
    }

    public MutableLiveData<List<Trailer>> getTrailerList() {
        return mRepository.getTrailerList();
    }

    public void queryTrailers(int movieID, String key) {
        mRepository.queryTrailers(movieID, key);
    }

    public MutableLiveData<List<Cast>> getCastList() {
        return mRepository.getCastList();
    }

    public void queryCast(int movieId, String key) {
        mRepository.queryCast(movieId, key);
    }

}
