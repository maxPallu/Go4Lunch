package com.maxpallu.go4lunch.util;

import com.maxpallu.go4lunch.models.Restaurants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantService {

    @GET("maps/api/place/nearbysearch/json?location=48.8707,2.3045&radius=1500&type=restaurant&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA")
    Call<Restaurants> getRestaurants();

    public static final Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("https://maps.googleapis.com")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

}
