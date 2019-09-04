package com.example.android.conde.com.cinephile.viewmodels;

import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.repositories.GenreFragmentsRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GenreFragmentsViewModel extends ViewModel {

    private GenreFragmentsRepository mRepository;

    public GenreFragmentsViewModel() {
        mRepository = GenreFragmentsRepository.getInstance();
    }

    public MutableLiveData<List<Movie>> getActionMovieGenreList() {
        return mRepository.getActionMovieGenreList();
    }

    public void queryMoviesByGenre(String key, String language, String region, String sortBy,
                                   boolean includeAdult, boolean includeVideo, int pageNumber, String withGenre){
        mRepository.queryMoviesByGenre(key, language, region, sortBy, includeAdult, includeVideo, pageNumber, withGenre);
    }

    public MutableLiveData<List<Movie>> getAdventureMovieGenreList() {
        return mRepository.getAdventureMovieGenreList();
    }

    public MutableLiveData<List<Movie>> getAnimationMovieGenreList() {
        return mRepository.getAnimationMovieGenreList();
    }

    public MutableLiveData<List<Movie>> getComedyMovieGenreList() {
        return mRepository.getComedyMovieGenreList();
    }

    public MutableLiveData<List<Movie>> getDocumentaryMovieGenreList() {
        return mRepository.getDocumentaryMovieGenreList();
    }


}
