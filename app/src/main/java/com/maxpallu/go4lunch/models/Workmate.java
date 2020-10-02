package com.maxpallu.go4lunch.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Workmate implements Parcelable {

    private String id;
    private String name;
    private String avatarUrl;
    private String restaurantId;
    private String restaurantName;

    public Workmate(String id, String name, String avatarUrl, String restaurantId, String restaurantName) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    protected Workmate(Parcel in) {
        id = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        restaurantId = in.readString();
        restaurantName = in.readString();
    }

    public static final Creator<Workmate> CREATOR = new Creator<Workmate>() {
        @Override
        public Workmate createFromParcel(Parcel in) {
            return new Workmate(in);
        }

        @Override
        public Workmate[] newArray(int size) {
            return new Workmate[size];
        }
    };

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatarUrl);
    }
}
