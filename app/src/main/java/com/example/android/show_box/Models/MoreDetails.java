package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoreDetails implements Parcelable {

    @SerializedName("budget")
    private final String budget;
    @SerializedName("status")
    private final String status;
    @SerializedName("runtime")
    private final String runtime;
    @SerializedName("revenue")
    private final String revenue;
    @SerializedName("tagline")
    private final String tagline;
    @SerializedName("genres")
    private List<Genre_POJO> genres;

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

    public void setGenres(List<Genre_POJO> genres) {
        this.genres = genres;
    }

    public static Creator<MoreDetails> getCREATOR() {
        return CREATOR;
    }
}
