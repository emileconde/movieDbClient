package com.example.android.conde.com.cinephile;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.android.conde.com.cinephile.adapters.MovieGenreAdapter;
import com.example.android.conde.com.cinephile.adapters.ItemClickListener;
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.util.FragmentToMovieDetailActivity;
import com.example.android.conde.com.cinephile.viewmodels.GenreFragmentsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdventureGenreFragment extends Fragment implements ItemClickListener {

    private RecyclerView mRecyclerView;
    private MovieGenreAdapter mMovieGenreAdapter;
    private List<Movie> mAdventureMovieList;
    private GenreFragmentsViewModel mGenreFragmentsViewModel;
    private ProgressBar mProgressBar;
    public AdventureGenreFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adventure_genre, container, false);
        mGenreFragmentsViewModel = ViewModelProviders.of(this).get(GenreFragmentsViewModel.class);
        mRecyclerView = view.findViewById(R.id.rv_adventure_genre);
        mProgressBar = view.findViewById(R.id.progress_bar_adventure_genre);
        showProgressBar();
        mAdventureMovieList = new ArrayList<>();
        mMovieGenreAdapter = new MovieGenreAdapter(getContext(), mAdventureMovieList, this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mMovieGenreAdapter);
        makeQueries();
        subscribeObservers();
        return view;
    }

    public void makeQueries(){
        mGenreFragmentsViewModel.queryMoviesByGenre(Constants.API_KEY,
                Constants.FILTER_LANGUAGE,
                Constants.FILTER_REGION,
                Constants.SORT_BY_POPULARITY,
                false,
                false,
                1,
                Constants.GENRE_ADVENTURE);
    }



    private void subscribeObservers(){
        mGenreFragmentsViewModel.getAdventureMovieGenreList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (mAdventureMovieList != null && mAdventureMovieList.size() > 0 )
                    mAdventureMovieList.clear();
                mAdventureMovieList.addAll(movies);
                mMovieGenreAdapter.notifyDataSetChanged();
                hideProgressBar();
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie, ImageView poster) {
        FragmentToMovieDetailActivity.getInstance(getContext())
                .moveToMovieDetailActivity(movie, poster, this);
    }

    @Override
    public void onCastClick(Cast cast, ImageView castMemberImage) {

    }

    @Override
    public void onProfileMovieClick(ProfileMovie profileMovie, ImageView profileImage) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                subscribeObservers();
            }
        }, Constants.DELAY_TIME);
    }


    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);

    }

}
