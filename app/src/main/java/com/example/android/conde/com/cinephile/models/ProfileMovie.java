package com.example.android.conde.com.cinephile.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProfileMovie implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("original_title")
    private String title;
    @SerializedName("backdrop_path")
    private String backDropPath;
    @SerializedName("overview")
    private String overView;
    @SerializedName("poster_path")
    private String posterPath;

    public ProfileMovie(int id, String releaseDate, float voteAverage, String title,
                        String backDropPath, String overView, String posterPath) {
        this.id = id;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.title = title;
        this.backDropPath = backDropPath;
        this.overView = overView;
        this.posterPath = posterPath;
    }


    protected ProfileMovie(Parcel in) {
        id = in.readInt();
        releaseDate = in.readString();
        voteAverage = in.readFloat();
        title = in.readString();
        backDropPath = in.readString();
        overView = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<ProfileMovie> CREATOR = new Creator<ProfileMovie>() {
        @Override
        public ProfileMovie createFromParcel(Parcel in) {
            return new ProfileMovie(in);
        }

        @Override
        public ProfileMovie[] newArray(int size) {
            return new ProfileMovie[size];
        }
    };

    public int getId() {
        return id;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(releaseDate);
        dest.writeFloat(voteAverage);
        dest.writeString(title);
        dest.writeString(backDropPath);
        dest.writeString(overView);
        dest.writeString(posterPath);
    }
}
