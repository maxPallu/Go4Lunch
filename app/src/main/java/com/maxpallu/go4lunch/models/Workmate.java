package com.maxpallu.go4lunch.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Workmate implements Parcelable {

    private long id;
    private String name;
    private String avatarUrl;

    public Workmate(long id, String name, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    protected Workmate(Parcel in) {
        id = in.readLong();
        name = in.readString();
        avatarUrl = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
