package com.maxpallu.go4lunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maxpallu.go4lunch.models.Result;

public class DetailActivity extends AppCompatActivity {

    private TextView detailName;
    private TextView detailAdress;
    private ImageView returnButton;
    private ImageView detailPicture;
    private FloatingActionButton call;
    private FloatingActionButton goRestaurant;
    private String restaurantPhone;
    private String restaurantWeb;

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

        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurantName");
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
}