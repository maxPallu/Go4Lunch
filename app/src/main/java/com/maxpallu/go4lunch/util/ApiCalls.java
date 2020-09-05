package com.maxpallu.go4lunch.util;

import androidx.annotation.Nullable;

import com.maxpallu.go4lunch.models.PlaceDetailsResponse;
import com.maxpallu.go4lunch.models.Restaurants;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCalls {

    public interface Callbacks {
        void onDetailsResponse(PlaceDetailsResponse placeDetailsResponse);
        void onResponse(@Nullable Restaurants restaurants);
        void onFailure();
    }

    public static void fetchRestaurant(Callbacks callbacks) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        RestaurantService restaurantService = RestaurantService.retrofit.create(RestaurantService.class);

        Call<Restaurants> call = restaurantService.getRestaurants();

        call.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                if(callbacksWeakReference.get() != null) {
                        callbacksWeakReference.get().onResponse(response.body());
                        Call<PlaceDetailsResponse> callDetails = restaurantService.getDetails(response.body().getResults().get(0).getPlaceId());
                        callDetails.enqueue(new Callback<PlaceDetailsResponse>() {
                            @Override
                            public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                                callbacksWeakReference.get().onDetailsResponse(response.body());
                            }

                            @Override
                            public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                                if(callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
                            }
                        });

                    }
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}
