package com.maxpallu.go4lunch.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.model.LatLng;
import com.maxpallu.go4lunch.DetailActivity;
import com.maxpallu.go4lunch.ListFragment;
import com.maxpallu.go4lunch.MapFragment;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.WorkmateRecyclerViewAdapter;
import com.maxpallu.go4lunch.WorkmatesFragment;
import com.maxpallu.go4lunch.api.Restaurant;
import com.maxpallu.go4lunch.api.RestaurantHelper;
import com.maxpallu.go4lunch.models.DetailsResult;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;
import com.maxpallu.go4lunch.util.ApiCalls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private List<Result> mResults = new ArrayList<Result>();
    private List<Result> mResultsFull;
    private List<DetailsResult> mDetails = new ArrayList<>();
    private Restaurants mRestaurants;
    private Context context;
    private LatLng userLatLng;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantName;
        private TextView restaurantAdress;
        private TextView restaurantHours;
        private TextView restaurantDistance;
        private ImageView resturantPicture;
        private ImageView restaurantRatings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAdress = itemView.findViewById(R.id.restaurant_adress);
            restaurantHours = itemView.findViewById(R.id.restaurant_hours);
            resturantPicture = itemView.findViewById(R.id.restaurant_picture);
            restaurantDistance = itemView.findViewById(R.id.restaurant_distance);
            restaurantRatings = itemView.findViewById(R.id.ratings);
        }
    }

    public MyAdapter(Restaurants restaurants) {
        mRestaurants = restaurants;
        mResultsFull = new ArrayList<>(mResults);
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

    public void updateDetails(DetailsResult result) {
        mDetails.add(result);
        notifyDataSetChanged();
    }

    public void updateLatLng(double lat, double lng) {
        userLatLng = new LatLng(lat, lng);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Result> currentRestaurant = mResults;
        DetailsResult details = getDetailsFromRestaurant(currentRestaurant.get(position));
        Intent intent = ((Activity) context).getIntent();

        RestaurantHelper.createRestaurant(currentRestaurant.get(position).getPlaceId(), currentRestaurant.get(position).getName(), currentRestaurant.get(position).getId());

        holder.restaurantName.setText(currentRestaurant.get(position).getName());

        holder.restaurantAdress.setText(currentRestaurant.get(position).getVicinity());

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
                holder.restaurantHours.setText("Aucune info");
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

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Result> filteredList = new ArrayList<>();
            filteredList = mResults;
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mResultsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Result result : mResultsFull) {
                    if (result.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(result);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mResults.clear();
            mResults.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}