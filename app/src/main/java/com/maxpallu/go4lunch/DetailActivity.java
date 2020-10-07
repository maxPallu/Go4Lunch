package com.maxpallu.go4lunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.User;
import com.maxpallu.go4lunch.api.UserHelper;
import com.maxpallu.go4lunch.api.WorkmateHelper;
import com.maxpallu.go4lunch.base.BaseActivity;
import com.maxpallu.go4lunch.di.DI;
import com.maxpallu.go4lunch.models.Result;
import com.maxpallu.go4lunch.models.Workmate;
import com.maxpallu.go4lunch.service.WorkmateApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private TextView detailName;
    private TextView detailAdress;
    private ImageView returnButton;
    private ImageView detailPicture;
    private FloatingActionButton call;
    private FloatingActionButton goRestaurant;
    private FloatingActionButton like;
    private String restaurantPhone;
    private String restaurantWeb;
    private String restaurantId;
    private String restaurantPicture;
    private String restaurantName;
    private String restaurantAdress;
    private WorkmateApiService mApiService;
    private List<Workmate> mWorkmates;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkmateRecyclerViewAdapter mAdapter = new WorkmateRecyclerViewAdapter(mWorkmates);
    private String userId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.restaurant_card_name);
        detailAdress = findViewById(R.id.restaurant_card_adress);
        returnButton = findViewById(R.id.return_button);
        detailPicture = findViewById(R.id.restaurantDetailPicture);
        call = findViewById(R.id.call);
        like = findViewById(R.id.like);
        goRestaurant = findViewById(R.id.select);

        mApiService = DI.getService();

        recyclerView = findViewById(R.id.list_client);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        restaurantId = intent.getStringExtra("restaurantId");

        restaurantName = intent.getStringExtra("restaurantName");
        restaurantAdress = intent.getStringExtra("restaurantAdress");
        restaurantPicture = intent.getStringExtra("restaurantPicture");
        restaurantPhone = intent.getStringExtra("restaurantPhone");
        restaurantWeb = intent.getStringExtra("restaurantWeb");

        WorkmateHelper.updateRestaurant(WorkmateHelper.getWorkmatesCollection().getId(), restaurantName, restaurantId);

        detailName.setText(restaurantName);
        detailAdress.setText(restaurantAdress);

        Glide.with(this.getApplicationContext()).load(restaurantPicture).apply(RequestOptions.centerCropTransform()).into(detailPicture);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        goRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = UserHelper.getUserId();
                FirebaseFirestore.getInstance().collection("user").document(userId).update("restaurantName", restaurantName);
                FirebaseFirestore.getInstance().collection("user").document(userId).update("restaurantId", restaurantId);
                FirebaseFirestore.getInstance().collection("user").document(userId).update("restaurantAdress", restaurantAdress);
                FirebaseFirestore.getInstance().collection("user").document(userId).update("restaurantPicture", restaurantPicture);
                FirebaseFirestore.getInstance().collection("user").document(userId).update("restaurantPhone", restaurantPhone);
                FirebaseFirestore.getInstance().collection("user").document(userId).update("restaurantWeb", restaurantWeb);
                goRestaurant.setBackgroundTintList(getResources().getColorStateList(R.color.restaurant));
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setBackgroundTintList(getResources().getColorStateList(R.color.like));
            }
        });
    }

    public void onCallButton(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String uri = "tel:" + restaurantPhone;
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public void goToUrl(View v) {
        Uri url = Uri.parse(restaurantWeb);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }

    private void initClientList() {
        mWorkmates = mApiService.getWorkmates();
        for(int i =0; i<mWorkmates.size(); i++) {
            if(mWorkmates.get(i).getRestaurantId() == restaurantId) {
                recyclerView.setAdapter(new WorkmateRecyclerViewAdapter(mWorkmates));
            }
        }

      // mWorkmates = mApiService.getWorkmates();
      // recyclerView.setAdapter(new WorkmateRecyclerViewAdapter(mWorkmates));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initClientList();
    }
}