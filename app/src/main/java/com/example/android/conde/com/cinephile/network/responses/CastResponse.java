package com.example.android.conde.com.cinephile.network.responses;

import com.example.android.conde.com.cinephile.models.Cast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
    @SerializedName("id")
    @Expose
    private int movieId;

    @SerializedName("cast")
    @Expose
    private List<Cast> mCastList;

    public List<Cast> getCastList() {
        return mCastList;
    }
}
