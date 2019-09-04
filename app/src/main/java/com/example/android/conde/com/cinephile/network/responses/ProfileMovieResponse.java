package com.example.android.conde.com.cinephile.network.responses;

import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileMovieResponse {
    @SerializedName("cast")
    @Expose
    private List<ProfileMovie> mProfileMovieList;

    public List<ProfileMovie> getProfileMovieList() {
        return mProfileMovieList;
    }
}
