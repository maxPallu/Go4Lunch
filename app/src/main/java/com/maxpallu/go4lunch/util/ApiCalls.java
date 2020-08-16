package com.maxpallu.go4lunch.util;

import androidx.annotation.Nullable;

import com.maxpallu.go4lunch.models.Restaurants;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCalls {

    public interface Callbacks {
        void onResponse(@Nullable List<Restaurants> restaurants);
        void onFailure();
    }

    public static void fetchRestaurant(Callbacks callbacks, String name) {
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        RestaurantService restaurantService = RestaurantService.retrofit.create(RestaurantService.class);

        Call<List<Restaurants>> call = restaurantService.getRestaurants(name);

        call.enqueue(new Callback<List<Restaurants>>() {
            @Override
            public void onResponse(Call<List<Restaurants>> call, Response<List<Restaurants>> response) {
                if(callbacksWeakReference.get() != null) {
                    callbacksWeakReference.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurants>> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}
