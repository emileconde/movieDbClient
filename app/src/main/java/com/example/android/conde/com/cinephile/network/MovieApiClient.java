package com.example.android.conde.com.cinephile.network;

import android.util.Log;

import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.models.Profile;
import com.example.android.conde.com.cinephile.models.ProfileMovie;
import com.example.android.conde.com.cinephile.models.Review;
import com.example.android.conde.com.cinephile.models.Trailer;
import com.example.android.conde.com.cinephile.network.responses.CastResponse;
import com.example.android.conde.com.cinephile.network.responses.MovieListResponse;
import com.example.android.conde.com.cinephile.network.responses.ProfileMovieResponse;
import com.example.android.conde.com.cinephile.network.responses.ReviewsResponse;
import com.example.android.conde.com.cinephile.network.responses.SimilarMoviesResponse;
import com.example.android.conde.com.cinephile.network.responses.TrailerResponse;
import com.example.android.conde.com.cinephile.threading.AppExecutors;
import com.example.android.conde.com.cinephile.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    private static final String TAG = "MovieApiClient";
    private static MovieApiClient mInstance;
    private MutableLiveData<List<Movie>> mMovieList;
    private MutableLiveData<List<Movie>> mNowPlayingList;
    private MutableLiveData<List<Movie>> mHighestRatedList;
    private MutableLiveData<List<Movie>> mActionMovieGenreList;
    private MutableLiveData<List<Movie>> mAdventureMovieGenreList;
    private MutableLiveData<List<Movie>> mAnimationMovieGenreList;
    private MutableLiveData<List<Movie>> mComedyMovieGenreList;
    private MutableLiveData<List<Movie>> mDocumentaryMovieGenreList;
    private MutableLiveData<List<Review>> mReviewList;
    private MutableLiveData<List<Trailer>> mTrailerList;
    private MutableLiveData<List<Cast>> mCastList;
    private MutableLiveData<List<Movie>> mSimilarMovies;
    private MutableLiveData<List<Movie>> mSearchedMoviesList;
    private MutableLiveData<Profile> mProfile;
    private MutableLiveData<List<ProfileMovie>> mProfileMovieList;
    private RetrieveMoviesRunnable mRetrieveMoviesRunnable;
    private RetrieveReviewsRunnable mRetrieveReviewsRunnable;
    private RetrieveTrailersRunnable mRetrieveTrailersRunnable;
    private RetrieveNowPlayingRunnable mNowPlayingRunnable;
    private RetrieveMoviesByGenreRunnable mRetrieveMoviesByGenreRunnable;
    private RetrieveCastRunnable mRetrieveCastRunnable;
    private RetrieveSimilarMoviesRunnable mRetrieveSimilarMoviesRunnable;
    private RetrieveProfileRunnable mRetrieveProfileRunnable;
    private RetrieveProfileMoviesRunnable mRetrieveProfileMoviesRunnable;
    private SearchMovieRunnable mSearchMovieRunnable;

    public static MovieApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new MovieApiClient();
        }
        return mInstance;
    }

    private MovieApiClient() {
        mMovieList = new MutableLiveData<>();
        mReviewList = new MutableLiveData<>();
        mTrailerList = new MutableLiveData<>();
        mNowPlayingList = new MutableLiveData<>();
        mHighestRatedList = new MutableLiveData<>();
        mActionMovieGenreList = new MutableLiveData<>();
        mAdventureMovieGenreList = new MutableLiveData<>();
        mAnimationMovieGenreList = new MutableLiveData<>();
        mComedyMovieGenreList = new MutableLiveData<>();
        mDocumentaryMovieGenreList = new MutableLiveData<>();
        mCastList = new MutableLiveData<>();
        mSimilarMovies = new MutableLiveData<>();
        mProfile = new MutableLiveData<>();
        mProfileMovieList = new MutableLiveData<>();
        mSearchedMoviesList = new MutableLiveData<>();
    }


    //Get the liveData to be observed
    public MutableLiveData<List<Movie>> getMovieList() {
        return mMovieList;
    }

    public MutableLiveData<List<Movie>> getNowPlayingList() {
        return mNowPlayingList;
    }

    public MutableLiveData<List<Review>> getReviewList() {
        return mReviewList;
    }

    public MutableLiveData<List<Trailer>> getTrailerList() {
        return mTrailerList;
    }

    public MutableLiveData<List<Movie>> getHighestRatedList() {
        return mHighestRatedList;
    }

    public MutableLiveData<List<Movie>> getActionMovieGenreList() {
        return mActionMovieGenreList;
    }


    public MutableLiveData<List<Movie>> getAdventureMovieGenreList() {
        return mAdventureMovieGenreList;
    }

    public MutableLiveData<List<Movie>> getAnimationMovieGenreList() {
        return mAnimationMovieGenreList;
    }

    public MutableLiveData<List<Movie>> getComedyMovieGenreList() {
        return mComedyMovieGenreList;
    }

    public MutableLiveData<List<Movie>> getDocumentaryMovieGenreList() {
        return mDocumentaryMovieGenreList;
    }

    public MutableLiveData<List<Cast>> getCastList() {
        return mCastList;
    }

    public MutableLiveData<List<Movie>> getSimilarMovies() {
        return mSimilarMovies;
    }


    public MutableLiveData<Profile> getProfile() {
        return mProfile;
    }

    public MutableLiveData<List<ProfileMovie>> getProfileMovieList() {
        return mProfileMovieList;
    }

    public MutableLiveData<List<Movie>> getSearchedMoviesList() {
        return mSearchedMoviesList;
    }

    /**
     * MOVIES
     */
    public void queryMovies(String key, String sortBy, boolean includeAdult,
                            boolean includeVideo, int pageNumber) {

        //Reset query if not null
        if (mRetrieveMoviesRunnable != null) {
            mRetrieveMoviesRunnable = null;
        }
        mRetrieveMoviesRunnable = new RetrieveMoviesRunnable(key, sortBy, includeAdult, includeVideo, pageNumber);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveMoviesRunnable);

        //Stop query after 3 seconds
        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }


    private class RetrieveMoviesRunnable extends MovieApiClient implements Runnable {
        private static final String TAG = "RetrieveMoviesRunnable";
        private String key;
        private String sortBy;
        private boolean includeAdult;
        private boolean includeVideo;
        private int pageNumber;

        public RetrieveMoviesRunnable(String key, String sortBy, boolean includeAdult, boolean includeVideo, int pageNumber) {
            this.key = key;
            this.sortBy = sortBy;
            this.includeAdult = includeAdult;
            this.includeVideo = includeVideo;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {
            try {
                Response response = getMoviesList(key, sortBy, includeAdult, includeVideo, pageNumber).execute();

                if (response.code() == Constants.RESPONSE_OK) {
                    MovieListResponse movieListResponse = (MovieListResponse) response.body();
                    List<Movie> movies = new ArrayList<>(movieListResponse.getMovies());
                    if (Constants.SORT_BY_POPULARITY.equals(sortBy)) {
                        if (pageNumber == 1) {
                            mMovieList.postValue(movies);
                        } else {
                            List<Movie> currentMovieList = mMovieList.getValue();
                            currentMovieList.addAll(movies);
                            mMovieList.postValue(currentMovieList);
                        }
                    } else if (Constants.SORT_BY_HIGHEST_RATED.equals(sortBy)) {
                        if (pageNumber == 1) {
                            mHighestRatedList.postValue(movies);
                        } else {
                            List<Movie> currentMovieList = mHighestRatedList.getValue();
                            currentMovieList.addAll(movies);
                            mHighestRatedList.postValue(currentMovieList);
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovieList.postValue(null);
                mHighestRatedList.postValue(null);
            }


        }

        private Call<MovieListResponse> getMoviesList(String key, String sortBy, boolean includeAdult,
                                                      boolean includeVideo, int pageNumber) {

            return ServiceGenerator.getMovieApi()
                    .getMovies(key, sortBy, includeAdult, includeVideo, pageNumber, Constants.FILTER_REGION);

        }
    }


    /**
     * MOVIES BY GENRE
     */


    public void queryMoviesByGenre(String key, String language, String region, String sortBy,
                                   boolean includeAdult, boolean includeVideo, int pageNumber, String withGenre) {

        //Reset query if not null
        if (mRetrieveMoviesByGenreRunnable != null) {
            mRetrieveMoviesByGenreRunnable = null;
        }
        mRetrieveMoviesByGenreRunnable = new RetrieveMoviesByGenreRunnable(key, language, region, sortBy,
                includeAdult, includeVideo, pageNumber, withGenre);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveMoviesByGenreRunnable);

        //Stop query after 3 seconds
        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }


    private class RetrieveMoviesByGenreRunnable implements Runnable {

        private String key;
        private String language;
        private String region;
        private String sortBy;
        private boolean includeAdult;
        private boolean includeVideo;
        private int pageNumber;
        private String withGenre;

        public RetrieveMoviesByGenreRunnable(String key, String language, String region, String sortBy,
                                             boolean includeAdult, boolean includeVideo, int pageNumber, String withGenre) {
            this.key = key;
            this.language = language;
            this.region = region;
            this.sortBy = sortBy;
            this.includeAdult = includeAdult;
            this.includeVideo = includeVideo;
            this.pageNumber = pageNumber;
            this.withGenre = withGenre;
        }

        @Override
        public void run() {

            try {
                Response response = getMoviesByGenre(key, language, region, sortBy, includeAdult,
                        includeVideo, pageNumber, withGenre).execute();
                if (response.code() == Constants.RESPONSE_OK) {
                    MovieListResponse movieListResponse = (MovieListResponse) response.body();
                    List<Movie> movieList = new ArrayList<>(movieListResponse.getMovies());
                    if (pageNumber == 1) {
                        if (Constants.GENRE_ACTION.equals(withGenre)) {
                            mActionMovieGenreList.postValue(movieList);
                        } else if (Constants.GENRE_ADVENTURE.equals(withGenre)) {
                            mAdventureMovieGenreList.postValue(movieList);
                        } else if (Constants.GENRE_ANIMATION.equals(withGenre)) {
                            mAnimationMovieGenreList.postValue(movieList);
                        } else if (Constants.GENRE_COMEDY.equals(withGenre)) {
                            mComedyMovieGenreList.postValue(movieList);
                        } else if (Constants.GENRE_DOCUMENTARY.equals(withGenre)) {
                            mDocumentaryMovieGenreList.postValue(movieList);
                        }
                    } else {
                        List<Movie> ml = mActionMovieGenreList.getValue();
                        ml.addAll(movieList);
                        mActionMovieGenreList.postValue(ml);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                mActionMovieGenreList.postValue(null);
            }

        }


        public Call<MovieListResponse> getMoviesByGenre(String key,
                                                        String language,
                                                        String region,
                                                        String sortBy,
                                                        boolean includeAdult,
                                                        boolean includeVideo,
                                                        int pageNumber,
                                                        String withGenre) {

            return ServiceGenerator.getMovieApi().getMoviesByGenre(key,
                    language,
                    region,
                    sortBy,
                    includeAdult, includeVideo,
                    pageNumber,
                    withGenre);

        }


    }


    /**
     * NOW PLAYING
     */

    public void queryNowPlayingMovies(String key) {

        if (mNowPlayingRunnable != null) {
            mNowPlayingRunnable = null;
        }

        mNowPlayingRunnable = new RetrieveNowPlayingRunnable(key);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mNowPlayingRunnable);


        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class RetrieveNowPlayingRunnable extends MovieApiClient implements Runnable {
        private String key;


        public RetrieveNowPlayingRunnable(String key) {
            this.key = key;

        }

        @Override
        public void run() {
            try {
                Response response = getNowPlayingList(key).execute();

                if (response.code() == Constants.RESPONSE_OK) {
                    MovieListResponse movieListResponse = (MovieListResponse) response.body();
                    List<Movie> movieList = new ArrayList<>(movieListResponse.getMovies());
                    mNowPlayingList.postValue(movieList);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mNowPlayingList.postValue(null);
            }


        }

        private Call<MovieListResponse> getNowPlayingList(String key) {

            return ServiceGenerator.getMovieApi()
                    .getNowPlaying(key);

        }
    }


    /**
     * REVIEWS
     */

    public void queryReviews(int movieID, String key) {
        if (mRetrieveReviewsRunnable != null) {
            mRetrieveReviewsRunnable = null;
        }

        mRetrieveReviewsRunnable = new RetrieveReviewsRunnable(movieID, key);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveReviewsRunnable);


        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }


    private class RetrieveReviewsRunnable implements Runnable {
        private int movieID;
        private String key;


        public RetrieveReviewsRunnable(int movieID, String key) {
            this.movieID = movieID;
            this.key = key;
        }

        @Override
        public void run() {
            try {
                Response response = getReviewsList(movieID, key).execute();

                if (response.code() == Constants.RESPONSE_OK) {
                    ReviewsResponse reviewResponse = (ReviewsResponse) response.body();
                    List<Review> reviews = new ArrayList<>(reviewResponse.getReviews());
                    for (Review review : reviews) {
                        Log.i(TAG, "run: " + review.getAuthor());
                    }
                    mReviewList.postValue(reviews);
                } else {
                    Log.e(TAG, "run: Response not OK");
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "run: " + e.getMessage());
                mReviewList.postValue(null);
            }


        }

        private Call<ReviewsResponse> getReviewsList(int movieID, String key) {

            return ServiceGenerator.getMovieApi()
                    .getReviews(movieID, key);

        }
    }


    /**
     * TRAILERS
     */

    public void queryTrailers(int movieID, String key) {

        if (mRetrieveTrailersRunnable != null) {
            mRetrieveTrailersRunnable = null;
        }

        mRetrieveTrailersRunnable = new RetrieveTrailersRunnable(movieID, key);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveTrailersRunnable);


        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }


    private class RetrieveTrailersRunnable extends MovieApiClient implements Runnable {
        private int movieID;
        private String key;


        public RetrieveTrailersRunnable(int movieID, String key) {
            this.movieID = movieID;
            this.key = key;
        }

        @Override
        public void run() {
            try {
                Response response = getTrailersList(movieID, key).execute();
                if (response.code() == Constants.RESPONSE_OK) {
                    TrailerResponse trailerResponse = (TrailerResponse) response.body();
                    List<Trailer> trailerList = new ArrayList<>(trailerResponse.getTrailers());
                    mTrailerList.postValue(trailerList);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTrailerList.postValue(null);
            }


        }

        private Call<TrailerResponse> getTrailersList(int movieID, String key) {

            return ServiceGenerator.getMovieApi()
                    .getTrailer(movieID, key);

        }
    }


    /**
     * GET CAST
     */


    public void queryCast(int movieId, String key) {
        if (mRetrieveCastRunnable != null) {
            mRetrieveCastRunnable = null;
        }
        mRetrieveCastRunnable = new RetrieveCastRunnable(movieId, key);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveCastRunnable);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }


    private class RetrieveCastRunnable implements Runnable {

        private int movieID;
        private String apiKey;

        public RetrieveCastRunnable(int movieID, String apiKey) {
            this.movieID = movieID;
            this.apiKey = apiKey;
        }

        @Override
        public void run() {
            try {
                Response response = getCast(movieID, apiKey).execute();
                if (response.code() == Constants.RESPONSE_OK) {
                    CastResponse castResponse = (CastResponse) response.body();
                    List<Cast> casts = new ArrayList<>(castResponse.getCastList());
                    mCastList.postValue(casts);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Call<CastResponse> getCast(int movieID, String apiKey) {
            return ServiceGenerator.getMovieApi().getCast(movieID, apiKey);
        }

    }


    /**
     * GET SIMILAR MOVIES
     */

    public void querySimilarMovies(int movieID, String apiKey){
        if(mRetrieveSimilarMoviesRunnable != null){
            mRetrieveSimilarMoviesRunnable = null;
        }

        mRetrieveSimilarMoviesRunnable = new RetrieveSimilarMoviesRunnable(movieID, apiKey);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveSimilarMoviesRunnable);


        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }



    private class RetrieveSimilarMoviesRunnable implements Runnable {
        private int movieID;
        private String apiKey;

        public RetrieveSimilarMoviesRunnable(int movieID, String apiKey) {
            this.movieID = movieID;
            this.apiKey = apiKey;
        }

        @Override
        public void run() {
            try {
                Response response = getSimilarMovies(movieID, apiKey).execute();
                if (response.code() == Constants.RESPONSE_OK) {
                    SimilarMoviesResponse similarMoviesResponse = (SimilarMoviesResponse) response.body();
                    List<Movie> similarMovies = new ArrayList<>(similarMoviesResponse.getSimilarMovies());
                    mSimilarMovies.postValue(similarMovies);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Call<SimilarMoviesResponse> getSimilarMovies(int movieID, String key) {
            return ServiceGenerator.getMovieApi().getSimilarMovies(movieID, key);
        }

    }


    /**
     * PROFILE
     * */

    public void queryProfile(int personID, String apiKey){
        if(mRetrieveProfileRunnable != null){
            mRetrieveProfileRunnable = null;
        }

        mRetrieveProfileRunnable = new RetrieveProfileRunnable(personID, apiKey);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveProfileRunnable);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);


    }



    private class RetrieveProfileRunnable implements Runnable{

        private int personID;
        private String apiKey;

        public RetrieveProfileRunnable(int personID, String apiKey) {
            this.personID = personID;
            this.apiKey = apiKey;
        }

        @Override
        public void run() {
            try {
                Response response = getProfile(personID, apiKey).execute();
                if(response.code() == Constants.RESPONSE_OK){
                    Profile profile = (Profile) response.body();
                    mProfile.postValue(profile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private Call<Profile> getProfile(int personID, String apiKey){
            return ServiceGenerator.getMovieApi().getProfile(personID, apiKey);
        }

    }










    /**
     * PROFILE MOVIE
     * */

    public void queryProfileMovies(int personID, String apiKey){
        if(mRetrieveProfileMoviesRunnable != null){
            mRetrieveProfileMoviesRunnable = null;
        }

        mRetrieveProfileMoviesRunnable = new RetrieveProfileMoviesRunnable(personID, apiKey);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mRetrieveProfileMoviesRunnable);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }




    private class RetrieveProfileMoviesRunnable implements Runnable{

        private int personID;
        private String apiKey;

        public RetrieveProfileMoviesRunnable(int personID, String apiKey) {
            this.personID = personID;
            this.apiKey = apiKey;
        }

        @Override
        public void run() {
            try {
                Response response = getProfileMovies(personID, apiKey).execute();
                if(response.code() == Constants.RESPONSE_OK){
                    ProfileMovieResponse profileMovieResponse = (ProfileMovieResponse) response.body();
                    List<ProfileMovie> profileMovies = profileMovieResponse.getProfileMovieList();
                    mProfileMovieList.postValue(profileMovies);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Call<ProfileMovieResponse> getProfileMovies(int personID, String apiKey){
            return ServiceGenerator.getMovieApi().getProfileMovies(personID, apiKey);
        }

    }


    /**
     * SEARCH
     * */

    public void searchMovies(String apiKey, String query, boolean includeAdult){
        if(mSearchMovieRunnable != null){
            mSearchMovieRunnable = null;
        }

        mSearchMovieRunnable = new SearchMovieRunnable(apiKey, query, includeAdult);
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(mSearchMovieRunnable);


        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);



    }



    private class SearchMovieRunnable implements Runnable{

        private String mApiKey;
        private String mQuery;
        private boolean mIncludeAdult;

        public SearchMovieRunnable(String apiKey, String query, boolean includeAdult) {
            mApiKey = apiKey;
            mQuery = query;
            mIncludeAdult = includeAdult;
        }

        @Override
        public void run() {

            try {
                Response response = searchMovie(mApiKey, mQuery, mIncludeAdult).execute();
                if (response.code() == Constants.RESPONSE_OK){
                    MovieListResponse movieListResponse = (MovieListResponse) response.body();
                    List<Movie> movies = movieListResponse.getMovies();
                    mSearchedMoviesList.postValue(movies);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        private Call<MovieListResponse> searchMovie(String apiKey,
                                                    String query,
                                                    boolean includeAdult){
            return ServiceGenerator.getMovieApi().searchMovies(apiKey, query, includeAdult);
        }

    }


}
