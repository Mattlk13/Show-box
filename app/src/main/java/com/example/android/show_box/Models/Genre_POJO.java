package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Genre_POJO implements Parcelable{
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;

    protected Genre_POJO(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Genre_POJO> CREATOR = new Creator<Genre_POJO>() {
        @Override
        public Genre_POJO createFromParcel(Parcel in) {
            return new Genre_POJO(in);
        }

        @Override
        public Genre_POJO[] newArray(int size) {
            return new Genre_POJO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Creator<Genre_POJO> getCREATOR() {
        return CREATOR;
    }
}
