package com.maxpallu.go4lunch.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Restaurant> mRestaurants;

    public static class  ViewHolder extends RecyclerView.ViewHolder {
        public TextView mRestaurantName;
        public TextView mRestaurantDistance;
        public TextView mRestaurantType;
        public TextView mRestaurantAdress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestaurantName = itemView.findViewById(R.id.restaurant_name);
            mRestaurantDistance = itemView.findViewById(R.id.restaurant_distance);
            mRestaurantType = itemView.findViewById(R.id.restaurant_type);
            mRestaurantAdress = itemView.findViewById(R.id.restaurant_adress);
        }
    }

    public MyAdapter(ArrayList<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant currentRestaurant = mRestaurants.get(position);

        // final CharSequence restaurantName = Place.Field.

        // holder.mRestaurantName.setText(currentRestaurant.getmName());
        // holder.mRestaurantDistance.setText(currentRestaurant.getmDistance());
        // holder.mRestaurantType.setText(currentRestaurant.getmType());
        // holder.mRestaurantAdress.setText(currentRestaurant.getmAdress());
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }
}
