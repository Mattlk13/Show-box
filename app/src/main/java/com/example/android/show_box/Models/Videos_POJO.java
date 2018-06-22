package com.example.android.show_box.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Videos_POJO implements Parcelable {

    @SerializedName("id")
    @Expose
    String id ;
    @SerializedName("key")
    @Expose
    String key;
    @SerializedName("size")
    @Expose
    String size;
    @SerializedName("type")
    @Expose
    String type;


    public Videos_POJO(String id, String key, String size, String type) {
        this.id = id;
        this.key = key;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Creator<Videos_POJO> getCREATOR() {
        return CREATOR;
    }



    protected Videos_POJO(Parcel in) {
        id = in.readString();
        key = in.readString();
        size = in.readString();
        type = in.readString();
    }

    public static final Creator<Videos_POJO> CREATOR = new Creator<Videos_POJO>() {
        @Override
        public Videos_POJO createFromParcel(Parcel in) {
            return new Videos_POJO(in);
        }

        @Override
        public Videos_POJO[] newArray(int size) {
            return new Videos_POJO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(size);
        dest.writeString(type);
    }
}
