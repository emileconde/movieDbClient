package com.example.android.conde.com.cinephile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.conde.com.cinephile.adapters.MovieAdapter;
import com.example.android.conde.com.cinephile.adapters.ItemClickListener;
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.viewmodels.SimilarMoviesFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SimilarMoviesFragment extends Fragment implements ItemClickListener {
    private static final String TAG = "Movie";
    private SimilarMoviesFragmentViewModel mSimilarMoviesFragmentViewModel;
    private RecyclerView mRvSimilarMovies;
    private int mMovieId = 0;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mSimilarMovieList;
    private SimilarMovieFragmentCommunicator mCommunicator;

    public interface SimilarMovieFragmentCommunicator {
        void onSimilarMovieClicked(Movie movie);
    }

    public SimilarMoviesFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.similar_movies_fragement_layout, container, false);
        if(getArguments() != null){
            mMovieId = getArguments().getInt(Constants.MOVIE_ID_KEY);
        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSimilarMoviesFragmentViewModel = ViewModelProviders.of(this).get(SimilarMoviesFragmentViewModel.class);
        mRvSimilarMovies = view.findViewById(R.id.rv_similar_movies);
        mSimilarMovieList = new ArrayList<>();
        makeQuery(mMovieId);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeObservers();
        displaySimilarMovies();
    }

    private void displaySimilarMovies() {
        mMovieAdapter = new MovieAdapter(getContext(), mSimilarMovieList, this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        mRvSimilarMovies.setAdapter(mMovieAdapter);
        mRvSimilarMovies.setLayoutManager(manager);
    }

    private void makeQuery(int movieId) {
        mSimilarMoviesFragmentViewModel.querySimilarMovies(movieId, Constants.API_KEY);
    }

    private void subscribeObservers() {
        mSimilarMoviesFragmentViewModel.getSimilarMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    if (mSimilarMovieList.size() > 0)
                        mSimilarMovieList.clear();
                    mSimilarMovieList.addAll(movies);
                    mMovieAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SimilarMovieFragmentCommunicator){
            mCommunicator = (SimilarMovieFragmentCommunicator) context;
        }else {
            throw new RuntimeException(context.toString()
                    +" Must implement SimilarMovieFragmentCommunicator");
        }
    }

    @Override
    public void onMovieClick(Movie movie, ImageView poster) {
        mCommunicator.onSimilarMovieClicked(movie);
    }

    @Override
    public void onCastClick(Cast cast, ImageView castMemberImage) {

    }

    @Override
    public void onProfileMovieClick(ProfileMovie profileMovie, ImageView profileImage) {

    }

}
