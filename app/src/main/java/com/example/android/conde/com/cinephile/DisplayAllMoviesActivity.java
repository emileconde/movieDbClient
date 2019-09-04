package com.example.android.conde.com.cinephile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.android.conde.com.cinephile.adapters.DisplayAllMoviesAdapter;
import com.example.android.conde.com.cinephile.adapters.ItemClickListener;
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.viewmodels.HomeActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayAllMoviesActivity extends AppCompatActivity implements ItemClickListener {
    private List<Movie> mPopularMovies;
    private List<Movie> mHighestRatedMovies;
    private HomeActivityViewModel mViewModel;
    private RecyclerView mRvDisplayAll;
    private DisplayAllMoviesAdapter mMovieAdapter;
    private ProgressBar mProgressBar;
    private List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_movies);
        init();
    }

    private void init() {
        int movieType = getIntent().getIntExtra(Constants.VIEW_ALL_MOVIES_EXTRA, 0);
        String query = getIntent().getStringExtra("query");
        String title = getIntent().getStringExtra(Constants.MOVIES_TYPE_EXTRA);
        mRvDisplayAll = findViewById(R.id.rv_display_all);
        mProgressBar = findViewById(R.id.display_all_progress_bar);
        showProgressBar();
        mPopularMovies = new ArrayList<>();
        mHighestRatedMovies = new ArrayList<>();
        mMovies = new ArrayList<>();
        mViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        if (movieType == Constants.VIEW_ALL_HIGHEST_RATED_MOVIES) {
            makeHighestRatedMovieQuery();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    subscribeHighestRatedMoviesObserver();
                }
            }, Constants.DELAY_TIME);
        } else if (movieType == Constants.VIEW_ALL_POPULAR_MOVIES) {
            makePopularMoviesQuery();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    subscribePopularMoviesObserver();
                }
            }, Constants.DELAY_TIME);
        } else {
            makeSearchQuery(Constants.API_KEY, query, false);
            subscribeSearchedMovies();
        }

        displayMovies();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

    }


    private void displayMovies() {
        mMovieAdapter = new DisplayAllMoviesAdapter(this, mMovies, this);
        GridLayoutManager manager = new GridLayoutManager(this, numberOfColumns());
        mRvDisplayAll.setAdapter(mMovieAdapter);
        mRvDisplayAll.setLayoutManager(manager);

        mRvDisplayAll.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!mRvDisplayAll.canScrollVertically(1)) {
                    mViewModel.searchNextMoviePage();
                }
            }
        });

    }


    private void makeSearchQuery(String apiKey, String query, boolean includeAdult) {
        mViewModel.searchMovies(apiKey, query, includeAdult);
    }


    private void makePopularMoviesQuery() {
        mViewModel.queryMovies(Constants.API_KEY, Constants.SORT_BY_POPULARITY,
                false, true, 1);

    }

    private void makeHighestRatedMovieQuery() {
        mViewModel.queryMovies(Constants.API_KEY, Constants.SORT_BY_HIGHEST_RATED,
                false, false, 1);
    }


    private void subscribePopularMoviesObserver() {
        mViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    if (mMovies.size() > 0)
                        mMovies.clear();
                    mMovies.addAll(movies);
                    mMovieAdapter.notifyDataSetChanged();
                    hideProgressBar();
                }
            }
        });
    }


    private void subscribeHighestRatedMoviesObserver() {
        mViewModel.getHighestRatedList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    if (mMovies.size() > 0)
                        mMovies.clear();
                    mMovies.addAll(movies);
                    mMovieAdapter.notifyDataSetChanged();
                    hideProgressBar();
                }
            }
        });
    }


    private void subscribeSearchedMovies() {
        mViewModel.getSearchedMoviesList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    if (mMovies.size() > 0)
                        mMovies.clear();
                    mMovies.addAll(movies);
                    mMovieAdapter.notifyDataSetChanged();
                    hideProgressBar();
                }
            }
        });
    }


    @Override
    public void onMovieClick(Movie movie, ImageView poster) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.SELECTED_MOVIE, movie);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                poster, "sharedName");

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCastClick(Cast cast, ImageView castMemberImage) {

    }

    @Override
    public void onProfileMovieClick(ProfileMovie profileMovie, ImageView profileImage) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRvDisplayAll.setVisibility(View.INVISIBLE);
    }

    private void hideProgressBar() {
        mRvDisplayAll.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}
