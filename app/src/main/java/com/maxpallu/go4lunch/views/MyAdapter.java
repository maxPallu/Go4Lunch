package com.maxpallu.go4lunch.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.maxpallu.go4lunch.DetailActivity;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.api.Restaurant;
import com.maxpallu.go4lunch.api.RestaurantHelper;
import com.maxpallu.go4lunch.di.DI;
import com.maxpallu.go4lunch.models.DetailsResult;
import com.maxpallu.go4lunch.models.PlaceAutocomplete;
import com.maxpallu.go4lunch.models.PredictionsItem;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;
import com.maxpallu.go4lunch.models.Workmate;
import com.maxpallu.go4lunch.util.ApiCalls;
import com.maxpallu.go4lunch.util.RestaurantService;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Result> mResults = new ArrayList<Result>();
    private List<DetailsResult> mDetails = new ArrayList<>();
    private List<DetailsResult> mAutoDetails = new ArrayList<>();
    private List<PredictionsItem> mAutocomplete = new ArrayList<>();
    private Context context;
    private LatLng userLatLng;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantName;
        private TextView restaurantAdress;
        private TextView restaurantHours;
        private TextView restaurantDistance;
        private ImageView resturantPicture;
        private ImageView restaurantRatings;
        private TextView user_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAdress = itemView.findViewById(R.id.restaurant_adress);
            restaurantHours = itemView.findViewById(R.id.restaurant_hours);
            resturantPicture = itemView.findViewById(R.id.restaurant_picture);
            restaurantDistance = itemView.findViewById(R.id.restaurant_distance);
            restaurantRatings = itemView.findViewById(R.id.ratings);
            user_number = itemView.findViewById(R.id.user_number);

        }
    }

    public MyAdapter() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        context = parent.getContext();

        return viewHolder;
    }

    public void updateResults(List<Result> results) {
        mResults.clear();
        mResults.addAll(results);
        notifyDataSetChanged();
    }

    private void updateResultsWithAutocomplete(Result results) {
        mResults.clear();
        mResults.add(results);
        notifyDataSetChanged();
    }

    public void updateWithAutocomple(List<PredictionsItem> results) {
        mAutocomplete.clear();
        mAutocomplete.addAll(results);
        notifyDataSetChanged();
    }

    public void updateDetails(DetailsResult result) {
        mDetails.add(result);
        notifyDataSetChanged();
    }

    public void updateLatLng(double lat, double lng) {
        userLatLng = new LatLng(lat, lng);
        notifyDataSetChanged();
    }

    public void getRestaurants(List<PredictionsItem> autocomplte) {
        for(int i =0; i<autocomplte.size(); i++) {
            for(DetailsResult result: mDetails) { if(result.getPlaceId().equals(autocomplte.get(i).getPlaceId())) {
                    mAutoDetails.add(result);
                    notifyDataSetChanged();
                    for(int j = 0; j<mAutoDetails.size(); j++) {
                        if(result != null) {
                            Result mResult = new Result();
                            mResult.setPlaceId(mAutoDetails.get(j).getPlaceId());
                            mResult.setId(mAutoDetails.get(j).getPlaceId());
                            mResult.setName(mAutoDetails.get(j).getName());
                            mResult.setVicinity(mAutoDetails.get(j).getVicinity());
                            mResult.setGeometry(mAutoDetails.get(j).getGeometry());
                            mResult.setPhotos(mAutoDetails.get(j).getPhotos());
                            updateResultsWithAutocomplete(mResult);
                            notifyDataSetChanged();
                        }
                    }

                }
            }
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Result> currentRestaurant = mResults;
        int numberWorkmate = 0;

        DetailsResult details = getDetailsFromRestaurant(currentRestaurant.get(position));
        Intent intent = ((Activity) context).getIntent();

        RestaurantHelper.createRestaurant(currentRestaurant.get(position).getPlaceId(), currentRestaurant.get(position).getName(), currentRestaurant.get(position).getPlaceId(), currentRestaurant.get(position).getVicinity(),
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&maxheight=800&photoreference=" + currentRestaurant.get(position).getPhotos().get(0).getPhotoReference() + "&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA");

        holder.restaurantName.setText(currentRestaurant.get(position).getName());

        holder.restaurantAdress.setText(currentRestaurant.get(position).getVicinity());

        List<Workmate> mWorkmates = DI.getService().getWorkmates();

        for(int i = 0; i<mWorkmates.size(); i++) {
            if(mWorkmates.get(i).getRestaurantName() != null) {
                if(mWorkmates.get(i).getRestaurantName().equals(currentRestaurant.get(position).getName())) {
                    numberWorkmate++;
                }
            }
            holder.user_number.setText("("+numberWorkmate+")");
        }

        if(userLatLng != null) {
            double userLatitude = userLatLng.latitude;
            double userLongitude = userLatLng.longitude;
            double restaurantLatitude = currentRestaurant.get(position).getGeometry().getLocation().getLat();
            double restaurantLongitude = currentRestaurant.get(position).getGeometry().getLocation().getLng();
            float dst;
            float results[] = new float[10];
            Location.distanceBetween(userLatitude, userLongitude, restaurantLatitude, restaurantLongitude, results);
            dst = results[0];
            String distance = Math.round(dst)+"m";
            holder.restaurantDistance.setText(distance);
        } else {
            holder.restaurantDistance.setText("Pas de distance");
        }

        if(details != null) {

            if(details.getOpeningHours() != null) {
                if (details.getOpeningHours().getOpenNow().equals(true)) {
                    holder.restaurantHours.setText(R.string.Open);
                } else {
                    holder.restaurantHours.setText(R.string.Close);
                }
            } else {
                holder.restaurantHours.setText(R.string.Info);
            }

            if(details.getRating() != null && details.getRating() <= 2) {
                holder.restaurantRatings.setImageResource(R.drawable.ic_baseline_star_24);
            } else if (details.getRating() != null) {
                holder.restaurantRatings.setImageResource(R.drawable.three_stars);
            }

        }

        Glide.with(context.getApplicationContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&maxheight=800&photoreference=" + currentRestaurant.get(position).getPhotos().get(0).getPhotoReference() + "&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA")
                .apply(RequestOptions.centerCropTransform())
                .into(holder.resturantPicture);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsActivity = new Intent(v.getContext(), DetailActivity.class);

                detailsActivity.putExtra("restaurantName", currentRestaurant.get(position).getName());

                detailsActivity.putExtra("restaurantId", currentRestaurant.get(position).getPlaceId());
                detailsActivity.putExtra("restaurantAdress", currentRestaurant.get(position).getVicinity());
                detailsActivity.putExtra("restaurantPicture", "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&maxheight=800&photoreference=" + currentRestaurant.get(position).getPhotos().get(0).getPhotoReference() + "&key=AIzaSyAcRMUsc5zeKZG5sxZz7-dk-CeT7PtudKA");
                detailsActivity.putExtra("restaurantPhone", details.getFormattedPhoneNumber());
                detailsActivity.putExtra("restaurantWeb", details.getWebsite());

                v.getContext().startActivity(detailsActivity);
            }
        });
    }

    private DetailsResult getDetailsFromRestaurant(Result result) {
        for (DetailsResult details: mDetails) {
            if (details.getPlaceId().equals(result.getPlaceId()))
                return details;
        }
        return null;
    }



    @Override
    public int getItemCount() {
        return mResults.size();
    }

}