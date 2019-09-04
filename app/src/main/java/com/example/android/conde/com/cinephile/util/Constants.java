package com.example.android.conde.com.cinephile.util;

import com.example.android.conde.com.cinephile.BuildConfig;

public class Constants {
    public static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String BACK_DROP_IMAGE_SIZE = "w1280";
    public static final String POSTER_IMAGE_SIZE = "w500";
    public static final String PROFILE_IMAGE_SIZE = "h632";
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String YOUTUBE_API_KEY = BuildConfig.YOUTUBE_API_KEY;
    public static final String YOUTUBE_LINK = "https://www.youtube.com/watch?v=";
    public static final String SORT_BY_POPULARITY = "popularity.desc";
    public static final String SORT_BY_HIGHEST_RATED = "vote_average.desc";
    public static final String FILTER_LANGUAGE = "en-US";
    public static final String FILTER_REGION = "US";
    public static final int NETWORK_TIMEOUT = 3000;
    public static final int RESPONSE_OK = 200;
    public static final int DELAY_TIME  = 900;

    //FragmentPager positions
    public static final int FRAGMENT_ACTION = 0;
    public static final int FRAGMENT_ADVENTURE = 1;
    public static final int FRAGMENT_ANIMATION = 2;
    public static final int FRAGMENT_COMEDY = 3;
    public static final int FRAGMENT_DOCUMENTARY = 4;
    public static final int NUMBER_OF_GENRE_ELEMENT = 5;

    //Genres
    public static final String GENRE_ACTION = "28";
    public static final String GENRE_ADVENTURE = "12";
    public static final String GENRE_ANIMATION = "16";
    public static final String GENRE_COMEDY = "35";
    public static final String GENRE_DOCUMENTARY = "99";

    //MovieDetailActivity poster or backdrop
    public static final int POSTER = 1;
    public static final int BACKDROP = 2;

    //key movieID
    public static final String MOVIE_ID_KEY = "movieID";
    public static final String  SELECTED_MOVIE = "selected_movie";

    //view all
    public static final int VIEW_ALL_POPULAR_MOVIES = 1;
    public static final int VIEW_ALL_HIGHEST_RATED_MOVIES = 2;
    public static final String VIEW_ALL_MOVIES_EXTRA = "view_all";


    //profiles viewpager fragment positions
    public static final int FRAGMENT_BIO = 0;
    public static final int FRAGMENT_FILMOGRAPHY = 1;
    public static final int NUMBER_OF_PROFILE_ELEMENT = 2;

    //Cast
    public static final String CAST_KEY = "cast";

    //extras
    public static final String MOVIES_TYPE_EXTRA = "movieTitle";


}
