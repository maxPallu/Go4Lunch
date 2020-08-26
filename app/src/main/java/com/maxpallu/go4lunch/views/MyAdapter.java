package com.maxpallu.go4lunch.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maxpallu.go4lunch.ListFragment;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.models.OpeningHours;
import com.maxpallu.go4lunch.models.Photo;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;
import com.maxpallu.go4lunch.util.ApiCalls;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Result> mResults = new ArrayList<Result>();
    private Restaurants mRestaurants;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantName;
        private TextView restaurantAdress;
        private TextView restaurantHours;
        private ImageView resturantPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAdress = itemView.findViewById(R.id.restaurant_adress);
            restaurantHours = itemView.findViewById(R.id.restaurant_hours);
            resturantPicture = itemView.findViewById(R.id.restaurant_picture);
        }
    }

    public MyAdapter(Restaurants restaurants) {
        mRestaurants = restaurants;
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



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Result> currentRestaurant = mResults;

        holder.restaurantName.setText(currentRestaurant.get(position).getName());
        if(currentRestaurant.get(position).getBusinessStatus().contains("OPERATIONAL")) {
            holder.restaurantHours.setText("Open");
        } else {
            holder.restaurantHours.setText("Close");
        }

        holder.restaurantAdress.setText(" -  "+currentRestaurant.get(position).getVicinity());

        Glide.with(context.getApplicationContext()).load(currentRestaurant.get(position).getPhotos()).into(holder.resturantPicture);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
