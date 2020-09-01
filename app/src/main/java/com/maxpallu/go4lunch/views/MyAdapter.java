package com.maxpallu.go4lunch.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maxpallu.go4lunch.DetailActivity;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.models.DetailsResult;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Result> mResults = new ArrayList<Result>();
    private List<DetailsResult> mDetails = new ArrayList<>();
    private Restaurants mRestaurants;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurantName;
        private TextView restaurantAdress;
        private TextView restaurantHours;
        private TextView restaurantDistance;
        private ImageView resturantPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAdress = itemView.findViewById(R.id.restaurant_adress);
            restaurantHours = itemView.findViewById(R.id.restaurant_hours);
            resturantPicture = itemView.findViewById(R.id.restaurant_picture);
            restaurantDistance = itemView.findViewById(R.id.restaurant_distance);
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

    public void updateDetails(DetailsResult result) {
        mDetails.add(result);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Result> currentRestaurant = mResults;

        holder.restaurantName.setText(currentRestaurant.get(position).getName());
        //if(currentRestaurant.get(position).getBusinessStatus().contains("OPERATIONAL")) {
            holder.restaurantHours.setText(R.string.Open);
        //} else {
        //    holder.restaurantHours.setText(R.string.Close);
        //}

        holder.restaurantAdress.setText(" -  "+currentRestaurant.get(position).getVicinity());

//        holder.restaurantDistance.setText(mDetails.get(1).getFormattedPhoneNumber());

        Glide.with(context.getApplicationContext()).load(currentRestaurant.get(position).getPhotos()).into(holder.resturantPicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsActivity = new Intent(v.getContext(), DetailActivity.class);
                v.getContext().startActivity(detailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
