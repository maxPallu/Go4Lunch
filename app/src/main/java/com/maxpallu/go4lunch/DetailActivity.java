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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.User;
import com.maxpallu.go4lunch.api.UserHelper;
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
    private String restaurantPhone;
    private String restaurantWeb;
    private WorkmateApiService mApiService;
    private List<Workmate> mWorkmates;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkmateRecyclerViewAdapter mAdapter = new WorkmateRecyclerViewAdapter(mWorkmates);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.restaurant_card_name);
        detailAdress = findViewById(R.id.restaurant_card_adress);
        returnButton = findViewById(R.id.return_button);
        detailPicture = findViewById(R.id.restaurantDetailPicture);
        call = findViewById(R.id.call);
        goRestaurant = findViewById(R.id.select);

        mApiService = DI.getService();

        recyclerView = findViewById(R.id.list_client);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurantName");
        String restaurantId = intent.getStringExtra("restaurantId");
        String restaurantAdress = intent.getStringExtra("restaurantAdress");
        String restaurantPicture = intent.getStringExtra("restaurantPicture");
        restaurantPhone = intent.getStringExtra("restaurantPhone");
        restaurantWeb = intent.getStringExtra("restaurantWeb");

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
                UserHelper.updateRestaurant(FirebaseFirestore.getInstance().collection("user").document().getId() ,restaurantName, restaurantId);
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
        recyclerView.setAdapter(new WorkmateRecyclerViewAdapter(mWorkmates));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initClientList();
    }
}