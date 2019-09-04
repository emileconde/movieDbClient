package com.example.android.conde.com.cinephile.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {
    //https://image.tmdb.org/t/p/w500+backdropPic
    //https://api.themoviedb.org/3/movie/157336/videos?api_key=2ade795a4eaa64cd3050f37583358eb3&language=en-US

    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String title;
    @SerializedName("backdrop_path")
    private String backDropPath;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overView;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private float voteCount;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie(int id, String title ,String backDropPath, String posterPath,
                 String overView, float voteAverage, float voteCount, String releaseDate) {
        this.id = id;
        this.backDropPath = backDropPath;
        this.posterPath = posterPath;
        this.overView = overView;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        backDropPath = in.readString();
        posterPath = in.readString();
        overView = in.readString();
        voteAverage = in.readFloat();
        voteCount = in.readFloat();
        title = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }


    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(float voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(backDropPath);
        dest.writeString(posterPath);
        dest.writeString(overView);
        dest.writeFloat(voteAverage);
        dest.writeFloat(voteCount);
        dest.writeString(title);
        dest.writeString(releaseDate);
    }
}
