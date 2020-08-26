package com.maxpallu.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;
import com.maxpallu.go4lunch.util.ApiCalls;
import com.maxpallu.go4lunch.views.MyAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements NetworkAsyncTask.Listeners, ApiCalls.Callbacks, Serializable {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Restaurants mRestaurants = new Restaurants();
    private List<Result> mResults;
    private MyAdapter mAdapter = new MyAdapter(mRestaurants);
    ;

    public ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    private void executeHttpRequestWithRetrofit() {
        ApiCalls.fetchRestaurant( this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.executeHttpRequestWithRetrofit();
        mResults = mRestaurants.getResults();
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

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    private void updateUIWithRestaurants(Restaurants restaurants) {
        mAdapter.updateResults(restaurants.getResults());
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
    public void onResponse(@Nullable Restaurants restaurants) {
        if(restaurants != null) {
            this.updateUIWithRestaurants(restaurants);
            mRestaurants = restaurants;
            mResults = restaurants.getResults();
        }
    }

    @Override
    public void onFailure() {

    }
}