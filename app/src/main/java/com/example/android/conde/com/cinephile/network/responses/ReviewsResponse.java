package com.example.android.conde.com.cinephile.network.responses;

import com.example.android.conde.com.cinephile.models.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<Review> mReviews;

    public List<Review> getReviews() {
        return mReviews;
    }
}
