package com.maxpallu.go4lunch.util;

import com.maxpallu.go4lunch.models.AutocompleteResult;
import com.maxpallu.go4lunch.models.PlaceAutocompleteResponse;
import com.maxpallu.go4lunch.models.PlaceDetailsResponse;
import com.maxpallu.go4lunch.models.Restaurants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantService {

    @GET("maps/api/place/nearbysearch/json?location=48.8707,2.3045&radius=1000&type=restaurant&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA")
    Call<Restaurants> getRestaurants();
    @GET("maps/api/place/details/json?fields=name,rating,formatted_phone_number,icon,website,place_id,opening_hours&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA")
    Call<PlaceDetailsResponse> getDetails(@Query("place_id") String placeId);
    @GET("maps/api/place/autocomplete/json?&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA")
    Call<AutocompleteResult> getAutocomplete(@Query("input") String input);

    public static final Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl("https://maps.googleapis.com")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

}
