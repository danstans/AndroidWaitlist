package com.example.danielstansberry.waitlist;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.gcm.GcmListenerService;

import butterknife.Bind;

/**
 * Created by Daniel Stansberry on 3/6/2016.
 */

public class PushNotification extends GcmListenerService {

   // @Bind(R.id.Confirm) Button confirmButton;
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        //confirmButton.setVisibility(View.VISIBLE);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {
                0,
                500, 200, 200, 500};
        vibrator.vibrate(pattern, -1);

        //Bitmap notifIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_dark);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_stat_apptab)
                .setContentTitle("Waitlist App").setContentText(message).setColor(Color.MAGENTA);
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        int mId=0;
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
