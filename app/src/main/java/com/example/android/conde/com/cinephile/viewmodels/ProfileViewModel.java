package com.example.android.conde.com.cinephile.viewmodels;

import com.example.android.conde.com.cinephile.models.Profile;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.repositories.ProfileRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel{
    private ProfileRepository mRepository;
    private MutableLiveData<Integer> castID;

    public ProfileViewModel(){
        mRepository = ProfileRepository.getInstance();
        castID = new MutableLiveData<>();
    }

    public void queryProfileMovies(int personID, String apiKey) {
        mRepository.queryProfileMovies(personID, apiKey);
    }

    public void queryProfile(int personID, String apiKey) {
        mRepository.queryProfile(personID, apiKey);
    }


    public MutableLiveData<Profile> getProfile() {
        return mRepository.getProfile();
    }

    public MutableLiveData<List<ProfileMovie>> getProfileMovieList() {
        return mRepository.getProfileMovieList();
    }


    public MutableLiveData<Integer> getCastID() {
        return castID;
    }
}
