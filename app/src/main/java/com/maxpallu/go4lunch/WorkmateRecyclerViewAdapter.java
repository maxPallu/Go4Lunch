package com.maxpallu.go4lunch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maxpallu.go4lunch.models.Workmate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkmateRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateRecyclerViewAdapter.ViewHolder> {

    private final List<Workmate> mWorkmates;

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
        Glide.with(holder.mWorkmateAvatar.getContext()).load(workmate.getAvatarUrl()).apply(RequestOptions.circleCropTransform())
                                            .into(holder.mWorkmateAvatar);
    }

    @Override
    public int getItemCount() {
        return mWorkmates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.workmate_avatar)
        public ImageView mWorkmateAvatar;
        @BindView(R.id.workmate_name)
        public TextView mWorkmateName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
