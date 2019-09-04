package com.example.android.conde.com.cinephile;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.conde.com.cinephile.adapters.ItemClickListener;
import com.example.android.conde.com.cinephile.adapters.ProfileMoviesAdapter;
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.util.Global;
import com.example.android.conde.com.cinephile.viewmodels.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActorFilmographyFragment extends Fragment implements ItemClickListener{
    private static final String TAG = "Movie";
    private ProfileViewModel mProfileViewModel;
    private List<ProfileMovie> mProfileMovieList;
    private RecyclerView mRvFilmography;
    private ProfileMoviesAdapter mProfileMoviesAdapter;
    public ActorFilmographyFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actor_filmography, container, false);
        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        mProfileMovieList = new ArrayList<>();
        mRvFilmography = view.findViewById(R.id.rv_filmography);
        mProfileMoviesAdapter = new ProfileMoviesAdapter(getContext(), mProfileMovieList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns());
        mRvFilmography.setAdapter(mProfileMoviesAdapter);
        mRvFilmography.setLayoutManager(gridLayoutManager);
        makeQueries(Global.castID);
        subscribeObservers();
        return view;
    }


    private void makeQueries(int castID){
        mProfileViewModel.queryProfileMovies(castID, Constants.API_KEY);
    }

    private void subscribeObservers(){
        mProfileViewModel.getProfileMovieList().observe(this, new Observer<List<ProfileMovie>>() {
            @Override
            public void onChanged(List<ProfileMovie> profileMovies) {
                if(profileMovies != null){
                    if(mProfileMovieList.size() > 0)
                        mProfileMovieList.clear();
                    mProfileMovieList.addAll(profileMovies);
                    mProfileMoviesAdapter.notifyDataSetChanged();

                }
            }
        });


    }

    @Override
    public void onMovieClick(Movie movie, ImageView imageView) {

    }

    @Override
    public void onCastClick(Cast cast, ImageView castMemberImage) {

    }

    @Override
    public void onProfileMovieClick(ProfileMovie profileMovie, ImageView profileImage) {

    }


    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        Global.castID = 0;
    }
}
