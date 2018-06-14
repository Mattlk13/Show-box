package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse implements Parcelable {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieDetails_POJO> results;
    @SerializedName("total_results")
    private long total_results;
    @SerializedName("total_pages")
    private long total_pages;

    protected MovieResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(MovieDetails_POJO.CREATOR);
        total_results = in.readLong();
        total_pages = in.readLong();
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieDetails_POJO> getResults() {
        return results;
    }

    public void setResults(List<MovieDetails_POJO> results) {
        this.results = results;
    }

    public long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }

    public long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(long total_pages) {
        this.total_pages = total_pages;
    }

    public MovieResponse(int page, List<MovieDetails_POJO> results, long total_results, long total_pages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedList(results);
        dest.writeLong(total_results);
        dest.writeLong(total_pages);
    }
}
