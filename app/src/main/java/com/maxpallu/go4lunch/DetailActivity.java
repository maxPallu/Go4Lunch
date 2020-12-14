package com.maxpallu.go4lunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
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
import com.maxpallu.go4lunch.notifications.ReminderBroadcast;
import com.maxpallu.go4lunch.service.WorkmateApiService;

import java.util.ArrayList;
import java.util.Calendar;
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
    private List<Workmate> mClients = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private WorkmateRecyclerViewAdapter mAdapter = new WorkmateRecyclerViewAdapter(mWorkmates);
    private String userId;
    private String CHANNEL_ID = "17";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private NotificationManager notificationManager;
    private Boolean createNotification = false;

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
        userId = UserHelper.getUserId();

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

                Bundle bundle = getIntent().getExtras();
                createNotification = bundle.getBoolean("Checked");
                createNotification();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("user").document(userId).update("like", true);
                like.setSupportImageTintList(getResources().getColorStateList(R.color.like));
            }
        });

        db.collection("user").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("restaurantName") != null) {
                    goRestaurant.setBackgroundTintList(getResources().getColorStateList(R.color.restaurant));
                }
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
            String workmateRestaurant = mWorkmates.get(i).getRestaurantName();
            if(restaurantName.equals(workmateRestaurant)) {
                mClients.add(mWorkmates.get(i));
            }
        }
        
        recyclerView.setAdapter(new WorkmateRecyclerViewAdapter(mClients));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initClientList();
    }

    private void createNotification() {

        Intent intent = new Intent(this.getApplicationContext(), ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if(createNotification == true) {
            createNotificationChannel();


            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            pendingIntent.cancel();
        }

    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}