package com.maxpallu.go4lunch.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxpallu.go4lunch.ListFragment;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Result> mResults = new ArrayList<Result>();
    private Restaurants mRestaurants;
    private Intent intent;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantName;
        private TextView restaurantAdress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAdress = itemView.findViewById(R.id.restaurant_adress);
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
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
