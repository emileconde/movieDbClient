package com.example.android.conde.com.cinephile.network;

import com.example.android.conde.com.cinephile.models.Profile;
import com.example.android.conde.com.cinephile.network.responses.CastResponse;
import com.example.android.conde.com.cinephile.network.responses.MovieListResponse;
import com.example.android.conde.com.cinephile.network.responses.ProfileMovieResponse;
import com.example.android.conde.com.cinephile.network.responses.ReviewsResponse;
import com.example.android.conde.com.cinephile.network.responses.SimilarMoviesResponse;
import com.example.android.conde.com.cinephile.network.responses.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//https://api.themoviedb.org/3/discover/
// movie?api_key=
// &language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&region=US
public interface MovieApi {
        @GET("discover/movie")
        Call<MovieListResponse> getMovies(@Query("api_key") String key,
                                          @Query("sort_by") String sortBy,
                                          @Query("include_adult") boolean includeAdult,
                                          @Query("include_video") boolean includeVideo,
                                          @Query("page") int page,
                                          @Query("region") String region);






        //https://api.themoviedb.org/3/movie/157336/videos?api_key=
        @GET("movie/{movie_id}/videos")
        Call<TrailerResponse> getTrailer(@Path("movie_id") int movieID ,
                                       @Query("api_key") String key);

        //https://api.themoviedb.org/3/movie/157336/reviews?api_key=
        @GET("movie/{movie_id}/reviews")
        Call<ReviewsResponse> getReviews(@Path("movie_id") int movieID,
                                         @Query("api_key") String key);


        //https://api.themoviedb.org/3/movie/now_playing?api_key=
        @GET("movie/now_playing")
        Call<MovieListResponse> getNowPlaying(@Query("api_key") String key);

        //https://api.themoviedb.org/3/discover/movie?api_key=
        // &language=en-US&region=US&sort_by=popularity.desc&include_adult=false&include_video=true&page=1&with_genres=16

        @GET("discover/movie")
        Call<MovieListResponse> getMoviesByGenre(@Query("api_key") String key,
                                                 @Query("language") String language,
                                                 @Query("region") String region,
                                                 @Query("sort_by") String sortBy,
                                                 @Query("include_adult") boolean includeAdult,
                                                 @Query("include_video") boolean includeVideo,
                                                 @Query("page") int page,
                                                 @Query("with_genres") String withGenre);


        //https://api.themoviedb.org/3/movie/384018/credits?api_key=
        @GET("movie/{movie_id}/credits")
        Call<CastResponse> getCast(@Path("movie_id") int movieId,
                                   @Query("api_key") String key);



        //https://api.themoviedb.org/3/movie/384018/similar?api_key=&language=en-US&page=1
        @GET("movie/{movie_id}/similar")
        Call<SimilarMoviesResponse> getSimilarMovies(
                @Path("movie_id") int movieID,
                @Query("api_key") String key);



        //https://api.themoviedb.org/3/person/18918/movie_credits?api_key=
        @GET("person/{person_id}/movie_credits")
        Call<ProfileMovieResponse> getProfileMovies(@Path("person_id") int personID,
                                                    @Query("api_key") String apiKey);



        //https://api.themoviedb.org/3/person/18918?api_key=
        @GET("person/{person_id}")
        Call<Profile> getProfile(@Path("person_id") int personID,
                                @Query("api_key") String apiKey);


        //https://api.themoviedb.org/3/search/movie?api_key=2ade795a4eaa64cd3050f37583358eb3
        // &language=en-US&query=the%20matrix&page=1&include_adult=false

        @GET("search/movie")
        Call<MovieListResponse> searchMovies(@Query("api_key") String key,
                                             @Query("query") String query,
                                             @Query("include_adult") boolean includeAdult);




}
