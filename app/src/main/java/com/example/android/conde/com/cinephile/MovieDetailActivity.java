package com.example.android.conde.com.cinephile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.conde.com.cinephile.adapters.CastRecyclerViewAdapter;
import com.example.android.conde.com.cinephile.adapters.CommentsRecyclerAdapter;
import com.example.android.conde.com.cinephile.adapters.ItemClickListener;
import com.example.android.conde.com.cinephile.adapters.TrailersRecyclerAdapter;
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.models.Review;
import com.example.android.conde.com.cinephile.models.Trailer;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.viewmodels.MovieDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailActivity extends AppCompatActivity
        implements SimilarMoviesFragment.SimilarMovieFragmentCommunicator, ItemClickListener {
    private static final String TAG = "MovieDetailActivity";
    private ImageView mBackdropImage;
    private ImageView mPosterImage;
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private TextView mOverView;

    private FloatingActionButton mPlayFab;
    private RecyclerView mRvComment;
    private CommentsRecyclerAdapter mCommentsAdapter;
    private TrailersRecyclerAdapter mTrailersAdapter;
    private List<Review> mReviewList;
    private List<Cast> mCastList;
    private MovieDetailViewModel mMovieDetailViewModel;
    private Movie mMovie;
    private CastRecyclerViewAdapter mCastRecyclerViewAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        iniViews();
        initCommentRecylerView();
        subscribeObservers();
        makeQueries(mMovie);
        //test();
        initCastRecyclerView();
//        initSimilarMovieRecyclerView();
    }

    void iniViews() {
        mMovie = getIntent().getParcelableExtra(Constants.SELECTED_MOVIE);
        //initialize
        mMovieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        mBackdropImage = findViewById(R.id.img_backdrop);
        mPosterImage = findViewById(R.id.img_poster);
        mTitle = findViewById(R.id.tv_movie_title);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mVoteAverage = findViewById(R.id.tv_vote_average);
        mOverView = findViewById(R.id.tv_overview);
        mPlayFab = findViewById(R.id.play_trailer_fab);
        mRvComment = findViewById(R.id.rv_comments);
        mReviewList = new ArrayList<>();
        mCastList = new ArrayList<>();
        displayMovieDetail(mMovie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mBackdropImage.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        mPlayFab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

        initSimilarMoviesFragment(mMovie);

    }


    private void displayMovieDetail(Movie movie) {
        inflateImageView(movie, Constants.POSTER, mPosterImage);
        inflateImageView(movie, Constants.BACKDROP, mBackdropImage);
        mTitle.setText(movie.getTitle());
        mReleaseDate.setText(String.format("\t%s", movie.getReleaseDate()));
        mVoteAverage.setText(String.format("\t%s", String.valueOf(mMovie.getVoteAverage())));
        mOverView.setText(movie.getOverView());
        getSupportActionBar().setTitle(movie.getTitle());
    }

    private void initSimilarMoviesFragment(Movie movie) {
        SimilarMoviesFragment similarMoviesFragment = new SimilarMoviesFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.MOVIE_ID_KEY, movie.getId());
        similarMoviesFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.similar_movies_container, similarMoviesFragment).commit();
    }

    private void initCastRecyclerView() {
        RecyclerView castRecyclerView = findViewById(R.id.rv_cast);
        mCastRecyclerViewAdapter = new CastRecyclerViewAdapter(this, mCastList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        castRecyclerView.setAdapter(mCastRecyclerViewAdapter);
        castRecyclerView.setLayoutManager(manager);
    }


    private void displayTrailers(List<Trailer> trailers) {
        RecyclerView trailersRecyclerView = findViewById(R.id.rv_trailers);
        mTrailersAdapter = new TrailersRecyclerAdapter(trailers, this);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        trailersRecyclerView.setAdapter(mTrailersAdapter);
        trailersRecyclerView.setLayoutManager(manager);
    }

    private void initCommentRecylerView() {
        mCommentsAdapter = new CommentsRecyclerAdapter(mReviewList);
        LinearLayoutManager manager =
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mRvComment.setAdapter(mCommentsAdapter);
        mRvComment.setLayoutManager(manager);
    }

    private void inflateImageView(Movie movie, int imgType, ImageView img) {
        String imgTypeSize = "";
        String path = "";
        if (imgType == Constants.POSTER) {
            path = movie.getPosterPath();
            imgTypeSize = Constants.POSTER_IMAGE_SIZE;
        } else if (imgType == Constants.BACKDROP) {
            path = movie.getBackDropPath();
            imgTypeSize = Constants.BACK_DROP_IMAGE_SIZE;
        }

        Glide.with(this)
                .load(Constants.BASE_IMAGE_URL + imgTypeSize
                        + path)
                //.placeholder(R)
                //.error(R.drawable.ic_profile_placeholder)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(img);
    }


    private void makeQueries(Movie movie) {
        //reviews
        mMovieDetailViewModel.queryReviews(movie.getId(), Constants.API_KEY);
        //trailers
        mMovieDetailViewModel.queryTrailers(movie.getId(), Constants.API_KEY);
        //cast
        mMovieDetailViewModel.queryCast(movie.getId(), Constants.API_KEY);
    }


    private void subscribeObservers() {
        //reviews
        mMovieDetailViewModel.getReviewList().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                if (mReviewList != null) {
                    if (mReviewList.size() > 0)
                        mReviewList.clear();
                    mReviewList.addAll(reviews);
                    mCommentsAdapter.notifyDataSetChanged();
                }

                Log.i(TAG, "Size of reviews " + reviews.size());

            }
        });


        //trailers
        mMovieDetailViewModel.getTrailerList().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                if (trailers != null) {
                    displayTrailers(trailers);
                }

            }
        });

        //cast
        mMovieDetailViewModel.getCastList().observe(this, new Observer<List<Cast>>() {
            @Override
            public void onChanged(List<Cast> casts) {
                if (casts != null) {
                    if (mCastList.size() > 0)
                        mCastList.clear();
                    mCastList.addAll(casts);
                    mCastRecyclerViewAdapter.notifyDataSetChanged();
                }

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
           finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onSimilarMovieClicked(Movie movie) {
        displayMovieDetail(movie);
        makeQueries(movie);
        initSimilarMoviesFragment(movie);
    }

    @Override
    public void onMovieClick(Movie movie, ImageView imageView) {

    }

    @Override
    public void onCastClick(Cast cast, ImageView castMemberImage) {
        Intent intent = new Intent(this, ProfileDetailActivity.class);
        intent.putExtra(Constants.CAST_KEY, cast);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                castMemberImage, "sharedName");

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onProfileMovieClick(ProfileMovie profileMovie, ImageView profileImage) {

    }
}
