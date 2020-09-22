package com.maxpallu.go4lunch.api;

public class User {

    private String id;
    private String name;
    private String email;
    private String restaurant;
    private String restaurantName;
    private String urlPicture;

    public User() {}

    public User(String id, String name, String mail, String picture) {
        this.id = id;
        this.email = mail;
        this.name = name;
        this.restaurant = "";
        this.restaurantName = "";
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

    public String getUrlPicture() {
        return urlPicture;
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

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }
}
