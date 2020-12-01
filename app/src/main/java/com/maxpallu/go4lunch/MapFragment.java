package com.maxpallu.go4lunch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.di.DI;
import com.maxpallu.go4lunch.models.DetailsResult;
import com.maxpallu.go4lunch.models.PlaceAutocomplete;
import com.maxpallu.go4lunch.models.PlaceDetailsResponse;
import com.maxpallu.go4lunch.models.PredictionsItem;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;
import com.maxpallu.go4lunch.models.Workmate;
import com.maxpallu.go4lunch.util.ApiCalls;
import com.maxpallu.go4lunch.util.PermissionUtils;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, ApiCalls.Callbacks {

    private SupportMapFragment mMapFragment;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap mMap;
    private Marker mMarker;
    private SearchView searchView;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<DetailsResult> mRestaurants = new ArrayList<>();

    public MapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    private void executeHttpRequestWithRetrofit() {
        ApiCalls.fetchRestaurant(this);
    }


    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.executeHttpRequestWithRetrofit();
        mMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
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
                executeRetrofit(newText);
                return true;
            }
        });
    }

    private void executeRetrofit(String input){
        ApiCalls.fetchAutocomplete(this, input);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this.getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.moveCamera(cameraUpdate);
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getContext());

        try {
            if(permissionDenied == false) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()) {
                            Location currentLocation = (Location) task.getResult();

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 16));
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
        enableMyLocation();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                mMap.moveCamera(cameraUpdate);
                return false;
            }
        });
        googleMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission((AppCompatActivity) this.getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    @Override
    public void onDetailsResponse(PlaceDetailsResponse placeDetailsResponse) {
        mRestaurants.add(placeDetailsResponse.getResult());
        String restaurantName;
        String restaurantId;

        for(int i = 0; i<mRestaurants.size(); i++) {
            restaurantName = mRestaurants.get(i).getName();
            restaurantId = mRestaurants.get(i).getPlaceId();
            double restaurantLat = mRestaurants.get(i).getGeometry().getLocation().getLat();
            double restaurantLng = mRestaurants.get(i).getGeometry().getLocation().getLng();

            LatLng restaurantLatLng = new LatLng(restaurantLat, restaurantLng);
            updateColor(restaurantId, restaurantName, restaurantLatLng);

            String name = restaurantName;
            String adress = mRestaurants.get(i).getVicinity();
            String phone = mRestaurants.get(i).getFormattedPhoneNumber();
            String web = mRestaurants.get(i).getWebsite();

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("restaurantName", name);
                    intent.putExtra("restaurantAdress", adress);
                    intent.putExtra("restaurantPhone", phone);
                    intent.putExtra("restaurantWeb", web);

                    startActivity(intent);
                }
            });
        }
    }

    private void updateColor(String id, String name, LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng)
                    .title(name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        mMarker = mMap.addMarker(markerOptions);
        mMarker.setTag(id);

        List<Workmate> mWorkmates = DI.getService().getWorkmates();

        for(int i =0; i<mWorkmates.size(); i++) {
            if(mWorkmates.get(i).getRestaurantName() != null) {
                if(mWorkmates.get(i).getRestaurantName().equals(name)) {
                    markerOptions.position(latLng)
                            .title(name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else {
                    markerOptions.position(latLng)
                            .title(name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                }
                mMarker = mMap.addMarker(markerOptions);
                mMarker.setTag(id);
            }
        }
    }

    @Override
    public void onResponse(@Nullable Restaurants restaurants) {

    }

    @Override
    public void onAutocompleteResponse(PlaceAutocomplete placeAutocompleteResponse) {
        List<PredictionsItem> mResults = placeAutocompleteResponse.getPredictions();
        for(int i=0; i<mResults.size(); i++) {
            for(DetailsResult result: mRestaurants) {
                if(result.getPlaceId().equals(mResults.get(i).getPlaceId())) {
                    List<DetailsResult> mDetails = new ArrayList<>();
                    mDetails.add(result);
                    for(int j=0; j<mDetails.size(); j++) {
                        if(result != null) {
                            double lat = mDetails.get(j).getGeometry().getLocation().getLat();
                            double lng = mDetails.get(j).getGeometry().getLocation().getLng();
                            LatLng latLng = new LatLng(lat, lng);

                            MarkerOptions markerOptions = new MarkerOptions();

                            markerOptions.position(latLng)
                                    .title(mDetails.get(j).getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                            mMap.clear();
                            mMarker = mMap.addMarker(markerOptions);
                            mMarker.setTag(mDetails.get(j).getPlaceId());
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
                            mMap.moveCamera(cameraUpdate);
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onFailure() {

    }
}