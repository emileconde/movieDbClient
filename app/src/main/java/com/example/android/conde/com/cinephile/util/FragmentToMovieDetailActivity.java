package com.example.android.conde.com.cinephile.util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.example.android.conde.com.cinephile.MovieDetailActivity;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;

import androidx.fragment.app.Fragment;

public class FragmentToMovieDetailActivity {
    private static FragmentToMovieDetailActivity sInstance;
    private Context mContext;
    public static FragmentToMovieDetailActivity getInstance(Context context){
        if(sInstance == null){
            sInstance = new FragmentToMovieDetailActivity(context);
        }
        return sInstance;
    }

    private FragmentToMovieDetailActivity(Context context) {
        mContext = context;
    }

    public void moveToMovieDetailActivity(Movie movie, ImageView poster, Fragment fragment){
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra(Constants.SELECTED_MOVIE, movie);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(fragment.getActivity(),
                poster, "sharedName");

        mContext.startActivity(intent, options.toBundle());
    }

     public void moveProfileMovieToMovieDetailActivity(ProfileMovie profileMovie, ImageView poster, Fragment fragment){
        Intent intent = new Intent(mContext, MovieDetailActivity.class);
        intent.putExtra(Constants.SELECTED_MOVIE, profileMovie);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(fragment.getActivity(),
                poster, "sharedName");

        mContext.startActivity(intent, options.toBundle());
    }







}
