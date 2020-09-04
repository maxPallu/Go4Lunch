package com.maxpallu.go4lunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxpallu.go4lunch.models.Result;

public class DetailActivity extends AppCompatActivity {

    private TextView detailName;
    private TextView detailAdress;
    private ImageView returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.restaurant_card_name);
        detailAdress = findViewById(R.id.restaurant_card_adress);
        returnButton = findViewById(R.id.return_button);

        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurantName");
        String restaurantAdress = intent.getStringExtra("restaurantAdress");

        detailName.setText(restaurantName);
        detailAdress.setText(restaurantAdress);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}