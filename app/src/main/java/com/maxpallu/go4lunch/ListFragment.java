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
import com.maxpallu.go4lunch.views.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements NetworkAsyncTask.Listeners {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    private void executeHttpRequest() {
        new NetworkAsyncTask( this).execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=48.8707,2.3045&radius=1500&type=restaurant&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        this.executeHttpRequest();
        return view;
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
}