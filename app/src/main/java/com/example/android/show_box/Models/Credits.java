package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Credits implements Parcelable {

    @SerializedName("cast")
    @Expose
    private List<Cast> cast;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew;

    protected Credits(Parcel in) {
        cast = in.createTypedArrayList(Cast.CREATOR);
        crew = in.createTypedArrayList(Crew.CREATOR);
    }

    public static final Creator<Credits> CREATOR = new Creator<Credits>() {
        @Override
        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        @Override
        public Credits[] newArray(int size) {
            return new Credits[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(cast);
        dest.writeTypedList(crew);
    }
}
