package com.maxpallu.go4lunch;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.WorkmateHelper;
import com.maxpallu.go4lunch.di.DI;
import com.maxpallu.go4lunch.models.Workmate;
import com.maxpallu.go4lunch.service.WorkmateApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkmatesFragment extends Fragment {

    private WorkmateApiService mApiService;
    private List<Workmate> mWorkmates;
    private RecyclerView mRecyclerView;
    private WorkmateRecyclerViewAdapter mAdapter = new WorkmateRecyclerViewAdapter(mWorkmates);
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static WorkmatesFragment newInstance() {
        WorkmatesFragment fragment = new WorkmatesFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_workmate_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private void initList() {
        mWorkmates = mApiService.getWorkmates();
        mRecyclerView.setAdapter(new WorkmateRecyclerViewAdapter(mWorkmates));
        for(int i = 0; i<mWorkmates.size(); i++) {
            Workmate workmate = new Workmate(mWorkmates.get(i).getId(), mWorkmates.get(i).getName(), mWorkmates.get(i).getAvatarUrl(), mWorkmates.get(i).getRestaurantId(), mWorkmates.get(i).getRestaurantName());
            db.collection("workmates").document(FirebaseFirestore.getInstance().collection("workmates")
                    .document().getId()).set(workmate);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }
}
