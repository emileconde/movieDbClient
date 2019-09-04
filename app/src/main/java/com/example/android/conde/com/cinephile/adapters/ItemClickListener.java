package com.example.android.conde.com.cinephile.adapters;

import android.widget.ImageView;

import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;


public interface ItemClickListener {

    void onMovieClick(Movie movie, ImageView imageView);
    void onCastClick(Cast cast, ImageView castMemberImage);
    void onProfileMovieClick(ProfileMovie profileMovie, ImageView profileImage);
}
