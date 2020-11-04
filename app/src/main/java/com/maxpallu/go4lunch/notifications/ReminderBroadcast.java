package com.maxpallu.go4lunch.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.R;
import com.maxpallu.go4lunch.api.UserHelper;
import com.maxpallu.go4lunch.di.DI;
import com.maxpallu.go4lunch.models.Workmate;
import com.maxpallu.go4lunch.service.WorkmateApiService;

import java.util.ArrayList;
import java.util.List;

public class ReminderBroadcast extends BroadcastReceiver {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String restaurant;
    private String userId = UserHelper.getUserId();
    private String CHANNEL_ID = "17";
    private String nameWorkmates;
    private int requestId = 100;
    private WorkmateApiService mApi = DI.getService();
    private List<Workmate> mWorkmates = mApi.getWorkmates();
    private List<Workmate> mClients = new ArrayList<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        getInformations(context);
    }

    private void getInformations(Context context) {
        db.collection("user").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                restaurant = (String) documentSnapshot.get("restaurantName");
                for(int i = 0; i<mWorkmates.size(); i++) {
                    String workmateRestaurant = mWorkmates.get(i).getRestaurantName();
                    if(restaurant.equals(workmateRestaurant)) {
                        mClients.add(mWorkmates.get(i));
                    }
                }

                sendNotification(context);
            }
        });
    }

    private void sendNotification(Context context) {

        for(int i = 0; i<mClients.size(); i++) {
            nameWorkmates = mClients.get(i).getName();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_image_notification)
                .setContentTitle("Rappel")
                .setContentText(context.getString(R.string.notif_choice)+restaurant+ context.getString(R.string.notif_with) +nameWorkmates)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());
    }
}
