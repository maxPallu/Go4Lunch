 package com.maxpallu.go4lunch;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.maxpallu.go4lunch.models.PlaceAutocomplete;
import com.maxpallu.go4lunch.models.PlaceDetailsResponse;
import com.maxpallu.go4lunch.models.PredictionsItem;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.util.ApiCalls;
import com.maxpallu.go4lunch.views.MyAdapter;

import java.io.Serializable;
import java.util.List;


 public class ListFragment extends Fragment implements NetworkAsyncTask.Listeners, ApiCalls.Callbacks, Serializable {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Restaurants mRestaurants = new Restaurants();
    private PlaceDetailsResponse mDetails = new PlaceDetailsResponse();
    private MyAdapter mAdapter = new MyAdapter();
    private Context context;
    private Boolean permissionDenied = false;
    private PlaceAutocomplete mResults;

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

        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                executeRetrofit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }

    private void executeRetrofit(String input){
        ApiCalls.fetchAutocomplete(this, input);
    }

    private void updateUIWithRestaurants(Restaurants restaurants) {
        mAdapter.updateResults(restaurants.getResults());
    }

    private void updateUIWithDetails(PlaceDetailsResponse response) {
        mAdapter.updateDetails(response.getResult());
    }

    private void updateUIWithAutocomplete(PlaceAutocomplete result) {
        mAdapter.updateWithAutocomple(result.getPredictions());
        mAdapter.getRestaurants(result.getPredictions());
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
     public void onAutocompleteResponse(PlaceAutocomplete placeAutocompleteResponse) {
         mResults = placeAutocompleteResponse;
         this.updateUIWithAutocomplete(mResults);
     }


     @Override
    public void onFailure() {

    }
}