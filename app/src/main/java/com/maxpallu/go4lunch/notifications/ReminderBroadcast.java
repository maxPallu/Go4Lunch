package com.maxpallu.go4lunch.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.UserHelper;

public class ReminderBroadcast extends BroadcastReceiver {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String restaurant;
    private String userId = UserHelper.getUserId();
    private String CHANNEL_ID = "17";
    private int requestId = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent =
                PendingIntent.getService(context, requestId, intent,
                        PendingIntent.FLAG_NO_CREATE);
    }
}
