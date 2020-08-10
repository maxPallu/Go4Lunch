package com.maxpallu.go4lunch;

import android.widget.TextView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import java.lang.reflect.Field;

public class Restaurant {

    private Place.Field mName;
    private Place.Field mDistance;
    private Place.Field mAdress;
    private Place.Field mType;

    public Restaurant(Place.Field name, Place.Field distance, Place.Field adress, Place.Field type) {
        mName = name;
        mDistance = distance;
        mAdress = adress;
        mType = type;
    }

    public Place.Field getmName() {
        return mName;
    }

    public void setmName(Place.Field mName) {
        this.mName = mName;
    }

    public Place.Field getmDistance() {
        return mDistance;
    }

    public void setmDistance(Place.Field mDistance) {
        this.mDistance = mDistance;
    }

    public Place.Field getmAdress() {
        return mAdress;
    }

    public void setmAdress(Place.Field mAdress) {
        this.mAdress = mAdress;
    }

    public Place.Field getmType() {
        return mType;
    }

    public void setmType(Place.Field mType) {
        this.mType = mType;
    }
}
