package com.maxpallu.go4lunch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxpallu.go4lunch.api.WorkmateHelper;
import com.maxpallu.go4lunch.di.DI;
import com.maxpallu.go4lunch.models.Workmate;
import com.maxpallu.go4lunch.service.WorkmateApiService;

import java.util.List;

public class WorkmatesFragment extends Fragment {

    private WorkmateApiService mApiService;
    private List<Workmate> mWorkmates;
    private RecyclerView mRecyclerView;
    private WorkmateRecyclerViewAdapter mAdapter = new WorkmateRecyclerViewAdapter(mWorkmates);

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
            WorkmateHelper.createWorkmate(mWorkmates.get(i).getId(), mWorkmates.get(i).getName(),
                    mWorkmates.get(i).getAvatarUrl());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }
}
