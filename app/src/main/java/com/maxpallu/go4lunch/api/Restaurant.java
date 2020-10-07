package com.maxpallu.go4lunch.api;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private String id;
    private String name;
    private String adress;
    private String picture;
    public Restaurant() {}

    public Restaurant(String name, String id, String adress, String picture) {
        this.name = name;
        this.id = id;
        this.adress = adress;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
