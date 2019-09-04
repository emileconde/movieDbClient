package com.example.android.conde.com.cinephile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.conde.com.cinephile.adapters.HighestRatedAdapter;
import com.example.android.conde.com.cinephile.adapters.ItemClickListener;
import com.example.android.conde.com.cinephile.adapters.MovieAdapter;
import com.example.android.conde.com.cinephile.adapters.MovieGenrePagerAdapter;
import com.example.android.conde.com.cinephile.adapters.SliderPagerAdapter;
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.receiver.ConnectionBroadcastReceiver;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.viewmodels.HomeActivityViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity
        implements ItemClickListener, View.OnClickListener, SearchView.OnQueryTextListener{
    private static final String TAG = "Home";
    private List<Movie> mSlideList;
    private List<Movie> mPopularMovieList;
    private List<Movie> mHighestRatedMovies;
    private ViewPager mSlidePager;
    private ViewPager mGenreViewPager;
    private TabLayout indicator;
    private RecyclerView MoviesRV;
    private RecyclerView mHighestRatedRecyclerView;
    private HomeActivityViewModel mViewModel;
    private SliderPagerAdapter mAdapter;
    private MovieAdapter mMovieAdapter;
    private HighestRatedAdapter mHighestRatedAdapter;
    private MovieGenrePagerAdapter mGenrePagerAdapter;
    private TextView mTvPopularMovies;
    private TextView mTvHighestRated;
    private SearchView mSearchView;
    private ConnectionBroadcastReceiver mConnectionBroadcastReceiver;
    private NestedScrollView mHomeActivityLayout;
    private LinearLayout mNoConnectionLayout;
    AnimationDrawable mNoConnectionDrawableAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mConnectionBroadcastReceiver = new ConnectionBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectionBroadcastReceiver, intentFilter);
        mHomeActivityLayout = findViewById(R.id.home_activity_layout);
        mNoConnectionLayout = findViewById(R.id.no_connection_include);

            initViews();
            subscribeObservers();
            makeQuery();
            displaySlides(mSlideList);
            displayPopularMovies(mPopularMovieList);
            disPlayHighestRatedMovies(mHighestRatedMovies);
            setUpTabLayout();
            mHomeActivityLayout.setVisibility(View.VISIBLE);
            mNoConnectionLayout.setVisibility(View.GONE);

    }


    private void initViews() {
        mSlidePager = findViewById(R.id.slider_pager);
        mGenreViewPager = findViewById(R.id.genre_viewPager);
        indicator = findViewById(R.id.indicator);
        MoviesRV = findViewById(R.id.Rv_popular_movies);
        mGenrePagerAdapter = new MovieGenrePagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);
        mHighestRatedRecyclerView = findViewById(R.id.rv_top_rated_movie);
        mSlideList = new ArrayList<>();
        mPopularMovieList = new ArrayList<>();
        mHighestRatedMovies = new ArrayList<>();

        mViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        mTvPopularMovies = findViewById(R.id.tv_popular);
        mTvHighestRated = findViewById(R.id.tv_top_rated);
        mTvPopularMovies.setOnClickListener(this);
        mTvHighestRated.setOnClickListener(this);

    }


    private void setUpTabLayout() {
        TabLayout tabLayout = findViewById(R.id.genre_tab_layout);
        mGenreViewPager.setAdapter(mGenrePagerAdapter);
        tabLayout.setupWithViewPager(mGenreViewPager);
        tabLayout.setTabTextColors(Color.WHITE, Color.parseColor("white"));
    }


    private void displayPopularMovies(List<Movie> movies) {
        mMovieAdapter = new MovieAdapter(this, movies, this);
        MoviesRV.setAdapter(mMovieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    }


    private void displaySlides(List<Movie> movies) {

        mAdapter =
                new SliderPagerAdapter(this, movies);
        mSlidePager.setAdapter(mAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager(mSlidePager, true);
    }


    private void disPlayHighestRatedMovies(List<Movie> movies) {
        mHighestRatedAdapter = new HighestRatedAdapter(this, movies, this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mHighestRatedRecyclerView.setAdapter(mHighestRatedAdapter);
        mHighestRatedRecyclerView.setLayoutManager(manager);
    }

    private void makeQuery() {
        mViewModel.queryMovies(Constants.API_KEY, Constants.SORT_BY_POPULARITY,
                false, true, 1);

        mViewModel.queryNowPlayingMovies(Constants.API_KEY);

        mViewModel.queryMovies(Constants.API_KEY, Constants.SORT_BY_HIGHEST_RATED,
                false, false, 1);

    }


    private void subscribeObservers() {
        mViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (mPopularMovieList != null & movies != null) {
                    mPopularMovieList.addAll(movies);
                    mMovieAdapter.notifyDataSetChanged();
                }
            }
        });

        mViewModel.getNowPlayingList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (mSlideList != null && movies != null) {
                    mSlideList.addAll(movies);
                    mSlideList.subList(4, mSlideList.size() - 1).clear();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


        mViewModel.getHighestRatedList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (mHighestRatedMovies != null && movies != null) {
                    mHighestRatedMovies.addAll(movies);
                    mHighestRatedAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie, ImageView imageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.SELECTED_MOVIE, movie);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this,
                imageView, "sharedName");

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCastClick(Cast cast, ImageView castMemberImage) {

    }

    @Override
    public void onProfileMovieClick(ProfileMovie profileMovie, ImageView profileImage) {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(HomeActivity.this, DisplayAllMoviesActivity.class);
        switch (v.getId()) {
            case R.id.tv_popular:
                intent.putExtra(Constants.VIEW_ALL_MOVIES_EXTRA, Constants.VIEW_ALL_POPULAR_MOVIES);
                intent.putExtra(Constants.MOVIES_TYPE_EXTRA, getString(R.string.pop_movies_title));
                break;
            case R.id.tv_top_rated:
                intent.putExtra(Constants.VIEW_ALL_MOVIES_EXTRA, Constants.VIEW_ALL_HIGHEST_RATED_MOVIES);
                intent.putExtra(Constants.MOVIES_TYPE_EXTRA, getString(R.string.top_rated_title));
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, DisplayAllMoviesActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }





    class SliderTimer extends TimerTask {
        @Override
        public void run() {

            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mSlidePager.getCurrentItem() < mSlideList.size() - 1) {
                        mSlidePager.setCurrentItem(mSlidePager.getCurrentItem() + 1);
                    } else
                        mSlidePager.setCurrentItem(0);
                }
            });


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) item.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnectionBroadcastReceiver);
    }


    @Override
    public void onBackPressed() {
        closeSearchView();
    }

    private void closeSearchView() {
        if (!mSearchView.isIconified()) {
            mSearchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }


    private void displayOfflineScreen() {
        ImageView ivNoConnectionLogo = findViewById(R.id.iv_no_connection_logo);
        ivNoConnectionLogo.setBackgroundResource(R.drawable.no_connection_animation);
        mNoConnectionDrawableAnimation = (AnimationDrawable) ivNoConnectionLogo.getBackground();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (mNoConnection)
//        mNoConnectionDrawableAnimation.start();
    }


}
