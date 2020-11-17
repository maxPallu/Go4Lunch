package com.maxpallu.go4lunch.util;

import androidx.annotation.Nullable;

import com.maxpallu.go4lunch.models.AutocompleteResult;
import com.maxpallu.go4lunch.models.PlaceAutocompleteResponse;
import com.maxpallu.go4lunch.models.PlaceDetailsResponse;
import com.maxpallu.go4lunch.models.Predictions;
import com.maxpallu.go4lunch.models.Restaurants;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCalls {

    public interface Callbacks {
        void onDetailsResponse(PlaceDetailsResponse placeDetailsResponse);
        void onResponse(@Nullable Restaurants restaurants);
        void onAutocompleteResponse(Predictions placeAutocompleteResponse);
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
                        for(int i=0; i<response.body().getResults().size(); i++) {
                            Call<PlaceDetailsResponse> callDetails = restaurantService.getDetails(response.body().getResults().get(i).getPlaceId());
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
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }

    public static void fetchAutocomplete(Callbacks callbacks, String input) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        RestaurantService restaurantService = RestaurantService.retrofit.create(RestaurantService.class);

        Call<Predictions> call = restaurantService.getAutocomplete(input);

        call.enqueue(new Callback<Predictions>() {
            @Override
            public void onResponse(Call<Predictions> call, Response<Predictions> response) {
                callbacksWeakReference.get().onAutocompleteResponse(response.body());
            }

            @Override
            public void onFailure(Call<Predictions> call, Throwable t) {
                callbacksWeakReference.get().onFailure();
            }
        });
    }
}
