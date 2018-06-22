package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos implements Parcelable {


    @SerializedName("results")
    private List<Videos_POJO> results;


    public List<Videos_POJO> getResults() {
        return results;
    }

    public void setResults(List<Videos_POJO> results) {
        this.results = results;
    }

    public static Creator<Videos> getCREATOR() {
        return CREATOR;
    }


    protected Videos(Parcel in) {
        results = in.createTypedArrayList(Videos_POJO.CREATOR);
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }
}
