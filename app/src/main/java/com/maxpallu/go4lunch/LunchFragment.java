package com.maxpallu.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.UserHelper;

public class LunchFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String restaurantName;
    private String restaurantAdress;
    private String restaurantPicture;
    private String restaurantPhone;
    private String restaurantWeb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db.collection("user").document(UserHelper.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                restaurantName = (String) documentSnapshot.get("restaurantName");
                restaurantAdress = (String) documentSnapshot.get("restaurantAdress");
                restaurantPicture = (String) documentSnapshot.get("restaurantPicture");
                restaurantPhone = (String) documentSnapshot.get("restaurantPhone");
                restaurantWeb = (String) documentSnapshot.get("restaurantWeb");

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("restaurantName", restaurantName);
                intent.putExtra("restaurantAdress", restaurantAdress);
                intent.putExtra("restaurantPicture", restaurantPicture);

                startActivity(intent);

            }
        });

        return inflater.inflate(R.layout.fragment_lunch, container, false);

    }
}
