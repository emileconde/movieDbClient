package com.example.android.conde.com.cinephile.network;

import com.example.android.conde.com.cinephile.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

      private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit sRetrofit = retrofitBuilder.build();

    private static MovieApi sMovieApi = sRetrofit.create(MovieApi.class);

    public static MovieApi getMovieApi() {
        return sMovieApi;
    }
}
