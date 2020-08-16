package com.maxpallu.go4lunch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.util.ApiCalls;
// import com.maxpallu.go4lunch.views.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements NetworkAsyncTask.Listeners, ApiCalls.Callbacks {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Restaurants restaurants = new Restaurants();


    public ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    private void executeHttpRequestWithRetrofit() {
        ApiCalls.fetchRestaurant( this, "Hotel Vernet, Champs - Élysées");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurants.getResults();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        restaurants.getResults();
        return view;
    }

    private void updateUIWithRestaurants(List<Restaurants> restaurants) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Restaurants restaurant : restaurants) {
            stringBuilder.append(restaurant.getResults());
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
    public void onResponse(@Nullable List<Restaurants> restaurants) {
        if(restaurants != null) this.updateUIWithRestaurants(restaurants);
    }

    @Override
    public void onFailure() {

    }
}