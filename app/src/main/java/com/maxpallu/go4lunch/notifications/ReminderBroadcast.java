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

public class ReminderBroadcast extends BroadcastReceiver {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String restaurant;
    private String userId = UserHelper.getUserId();
    private String CHANNEL_ID = "17";
    private int requestId = 100;


    @Override
    public void onReceive(Context context, Intent intent) {
        getInformations();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_image_notification)
                .setContentTitle("Rappel")
                .setContentText("Vous mangez à "+restaurant+" avec vos collègues !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());
    }

    private void getInformations() {
        db.collection("user").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                restaurant = (String) documentSnapshot.get("restaurantName");
            }
        });
    }
}
