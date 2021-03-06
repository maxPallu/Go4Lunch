package com.maxpallu.go4lunch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maxpallu.go4lunch.models.Restaurants;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Restaurants> mRestaurant;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView restaurantName;
        public TextView restaurantAdress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAdress = itemView.findViewById(R.id.restaurant_adress);
        }
    }

    public ListAdapter(List<Restaurants> restaurant) {
        mRestaurant = restaurant;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item, parent, false);
        ListAdapter.ViewHolder vh = new ListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurants currentRestaurant = mRestaurant.get(position);

        holder.restaurantName.setText(currentRestaurant.getResults().toString());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
