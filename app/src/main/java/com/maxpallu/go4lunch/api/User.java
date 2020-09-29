package com.maxpallu.go4lunch.api;

import android.net.Uri;

public class User {

    private String id;
    private String name;
    private String email;
    private String restaurant;
    private String restaurantId;
    private String restaurantName;
    private Uri urlPicture;

    public User() {}

    public User(String id, String name, String mail, Uri picture, String restaurantName, String restaurantId) {
        this.id = id;
        this.email = mail;
        this.name = name;
        this.restaurantId = restaurantId;
        this.restaurant = "";
        this.restaurantName = restaurantName;
        this.urlPicture = picture;
    }

    // GETTERS


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Uri getUrlPicture() {
        return urlPicture;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    // SETTERS


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setUrlPicture(Uri urlPicture) {
        this.urlPicture = urlPicture;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
