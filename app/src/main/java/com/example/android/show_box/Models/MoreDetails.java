package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoreDetails implements Parcelable {

    @SerializedName("budget")
    @Expose
    private final String budget;
    @SerializedName("status")
    @Expose
    private final String status;
    @SerializedName("runtime")
    @Expose
    private final String runtime;
    @SerializedName("revenue")
    @Expose
    private final String revenue;
    @SerializedName("tagline")
    @Expose
    private final String tagline;
    @SerializedName("genres")
    @Expose
    private List<Genre_POJO> genres;
    @SerializedName("videos")
    @Expose
    Videos videos;
    @SerializedName("credits")
    @Expose
    private Credits credits;
    @SerializedName("reviews")
    @Expose
    private Reviews reviews;



    public MoreDetails(String budget, List<Genre_POJO> genres, String status, String runtime, String revenue, String tagline) {
        this.budget = budget;
        this.genres = genres;
        this.status = status;
        this.runtime = runtime;
        this.revenue = revenue;
        this.tagline = tagline;
    }

    protected MoreDetails(Parcel in) {
        budget = in.readString();
        genres = in.createTypedArrayList(Genre_POJO.CREATOR);
        status = in.readString();
        runtime = in.readString();
        revenue = in.readString();
        tagline = in.readString();
        videos = in.readParcelable(Videos.class.getClassLoader());
        credits = in.readParcelable(Credits.class.getClassLoader());
        reviews = in.readParcelable(Reviews.class.getClassLoader());
    }

    public static final Creator<MoreDetails> CREATOR = new Creator<MoreDetails>() {
        @Override
        public MoreDetails createFromParcel(Parcel in) {
            return new MoreDetails(in);
        }

        @Override
        public MoreDetails[] newArray(int size) {
            return new MoreDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(budget);
        dest.writeTypedList(genres);
        dest.writeString(status);
        dest.writeString(runtime);
        dest.writeString(revenue);
        dest.writeString(tagline);
        dest.writeParcelable(videos, flags);
        dest.writeParcelable(credits, flags);
        dest.writeParcelable(reviews, flags);
    }

    public String getBudget() {
        return budget;
    }

    public String getStatus() {
        return status;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getTagline() {
        return tagline;
    }

    public List<Genre_POJO> getGenres() {
        return genres;
    }
    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }
    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public void setGenres(List<Genre_POJO> genres) {
        this.genres = genres;
    }

    public static Creator<MoreDetails> getCREATOR() {
        return CREATOR;
    }
}
