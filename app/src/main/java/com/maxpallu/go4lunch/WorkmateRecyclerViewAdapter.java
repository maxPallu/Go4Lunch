package com.maxpallu.go4lunch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.UserHelper;
import com.maxpallu.go4lunch.api.WorkmateHelper;
import com.maxpallu.go4lunch.models.Workmate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkmateRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateRecyclerViewAdapter.ViewHolder> {

    private final List<Workmate> mWorkmates;
    private Context context;
    private String restaurantName;

    public WorkmateRecyclerViewAdapter(List<Workmate> items) {mWorkmates = items;}

    @NonNull
    @Override
    public WorkmateRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workmates, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmateRecyclerViewAdapter.ViewHolder holder, int position) {
        Workmate workmate = mWorkmates.get(position);
        holder.mWorkmateName.setText(workmate.getName());

        if(workmate.getRestaurantId() != null) {
            FirebaseFirestore.getInstance().collection("workmates")
                    .document(WorkmateHelper.getWorkmatesCollection().getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    restaurantName = (String) documentSnapshot.get("restaurantName");
                }
            });

            holder.mWorkmateRestaurant.setText(" is eating " +restaurantName);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsActivity = new Intent(v.getContext(), DetailActivity.class);
                    v.getContext().startActivity(detailsActivity);
                }
            });
        } else {
            holder.mWorkmateRestaurant.setText(" hasn't decided yet");
            holder.mWorkmateName.setTextColor(Color.parseColor("#bfbebd"));
            holder.mWorkmateName.setTypeface(Typeface.DEFAULT);
            holder.mWorkmateRestaurant.setTypeface(Typeface.DEFAULT);
            holder.mWorkmateRestaurant.setTextColor(Color.parseColor("#bfbebd"));
        }

        Glide.with(holder.mWorkmateAvatar.getContext()).load(workmate.getAvatarUrl()).apply(RequestOptions.circleCropTransform())
                                            .into(holder.mWorkmateAvatar);

    }

    @Override
    public int getItemCount() {
        return mWorkmates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.workmate_avatar)
        public ImageView mWorkmateAvatar;
        @BindView(R.id.workmate_name)
        public TextView mWorkmateName;
        @BindView(R.id.workmate_restaurant)
        public TextView mWorkmateRestaurant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
