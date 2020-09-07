package com.maxpallu.go4lunch.views;

import android.content.Context;
import android.content.Intent;
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
import com.maxpallu.go4lunch.DetailActivity;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.models.DetailsResult;
import com.maxpallu.go4lunch.models.Restaurants;
import com.maxpallu.go4lunch.models.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private List<Result> mResults = new ArrayList<Result>();
    private List<Result> mResultsFull;
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


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Result> currentRestaurant = mResults;
        List<DetailsResult> currentDetail = mDetails;

        holder.restaurantName.setText(currentRestaurant.get(position).getName());

        holder.restaurantHours.setText(R.string.Open);

        holder.restaurantAdress.setText(currentRestaurant.get(position).getVicinity());

        if(currentDetail == null || currentDetail.isEmpty()) {
            holder.restaurantDistance.setText("Aucun avis");
        } else {
            holder.restaurantDistance.setText(" -  "+currentDetail.get(0).getRating().toString());
        }

        // Glide.with(context.getApplicationContext()).load(currentDetail.get(0).getIcon()).into(holder.resturantPicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsActivity = new Intent(v.getContext(), DetailActivity.class);

                detailsActivity.putExtra("restaurantName", currentRestaurant.get(position).getName());
                detailsActivity.putExtra("restaurantAdress", currentRestaurant.get(position).getVicinity());

                v.getContext().startActivity(detailsActivity);
            }
        });
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

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(mResultsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Result result : mResultsFull) {
                    if(result.getName().toLowerCase().contains(filterPattern)) {
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
