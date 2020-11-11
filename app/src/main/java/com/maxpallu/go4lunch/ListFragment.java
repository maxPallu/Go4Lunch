package com.maxpallu.go4lunch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.maxpallu.go4lunch.models.AutocompleteResult;
import com.maxpallu.go4lunch.models.DetailsResult;
import com.maxpallu.go4lunch.models.PlaceAutocompleteResponse;
import com.maxpallu.go4lunch.models.PlaceDetailsResponse;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;
import com.maxpallu.go4lunch.util.ApiCalls;
import com.maxpallu.go4lunch.util.RestaurantService;
import com.maxpallu.go4lunch.views.MyAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class ListFragment extends Fragment implements NetworkAsyncTask.Listeners, ApiCalls.Callbacks, Serializable {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Restaurants mRestaurants = new Restaurants();
    private PlaceDetailsResponse mDetails = new PlaceDetailsResponse();
    private MyAdapter mAdapter = new MyAdapter(mRestaurants);
    private Context context;
    private Boolean permissionDenied = false;

    public ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    private void executeHttpRequestWithRetrofit() {
        ApiCalls.fetchRestaurant(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.executeHttpRequestWithRetrofit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.list_recycler_view);
        mLayoutManager = new LinearLayoutManager(this.getContext());

        getDeviceLocation();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    private void updateUIWithRestaurants(Restaurants restaurants) {
        mAdapter.updateResults(restaurants.getResults());
    }

    private void updateUIWithDetails(PlaceDetailsResponse response) {
        mAdapter.updateDetails(response.getResult());
    }

    private void updateUIWithAutocomplete(AutocompleteResult result) {
        mAdapter.updateWithAutocomple(result);
    }

    private void updateLocation(double lat, double lng) {
        mAdapter.updateLatLng(lat, lng);
    }

    private void getDeviceLocation() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this.getContext());

        try {
            if(permissionDenied == false) {
                Task location = client.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()) {
                            Location loc = (Location) task.getResult();

                            double Latitude = loc.getLatitude();
                            double Longitude = loc.getLongitude();
                            updateLocation(Latitude, Longitude);
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(String success) {

    }



    @Override
    public void onDetailsResponse(PlaceDetailsResponse placeDetailsResponse) {
        if(placeDetailsResponse != null) {
            this.updateUIWithDetails(placeDetailsResponse);
            mDetails = placeDetailsResponse;
        }
    }

    @Override
    public void onResponse(@Nullable Restaurants restaurants) {
        if(restaurants != null) {
            this.updateUIWithRestaurants(restaurants);
            mRestaurants = restaurants;
        }
    }

    @Override
    public void onAutocompleteResponse(AutocompleteResult placeAutocompleteResponse) {
        if(placeAutocompleteResponse != null) {
            this.updateUIWithAutocomplete(placeAutocompleteResponse);
        }
    }


    @Override
    public void onFailure() {

    }
}