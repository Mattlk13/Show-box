package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieDetails_POJO implements Parcelable {

    @SerializedName("title")
    private final String title;
    @SerializedName("poster_path")
    private final String posterPath;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("vote_average")
    private final String voteAverage;
    @SerializedName("release_date")
    private final String releaseDate;
    @SerializedName("backdrop_path")
    private final String backdrop_path;




    public MovieDetails_POJO(String title, String posterPath, String overview, String voteAverage, String releaseDate, String backdrop_path) {
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.backdrop_path = backdrop_path;
    }

    protected MovieDetails_POJO(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
        backdrop_path = in.readString();
    }

    public static final Creator<MovieDetails_POJO> CREATOR = new Creator<MovieDetails_POJO>() {
        @Override
        public MovieDetails_POJO createFromParcel(Parcel in) {
            return new MovieDetails_POJO(in);
        }

        @Override
        public MovieDetails_POJO[] newArray(int size) {
            return new MovieDetails_POJO[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public String getBackdrop_path() {
        return backdrop_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(backdrop_path);
    }
}
