package com.example.android.conde.com.cinephile.network.responses;

import com.example.android.conde.com.cinephile.models.Trailer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("results")
    @Expose
    private List<Trailer> mTrailers;

    public List<Trailer> getTrailers() {
        return mTrailers;
    }
}
