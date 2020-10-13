package com.yoyo.airquality;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    public NotificationHelper(Context context) {
        this.mContext = context;
    }

    private NotificationCompat.Builder mBuilder;
    private String mChannelId;
    private NotificationManagerCompat mNotificationManagerCompat;
    private Context mContext;


    public void createNotification(String textToShow, String channelId) {

        createChannelId(channelId);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder = new NotificationCompat.Builder(mContext, mChannelId)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentText(textToShow)
                .setAutoCancel(true)
                .setChannelId(mChannelId)
                .setSound(defaultSoundUri);

        mNotificationManagerCompat.notify(1, mBuilder.build());
    }

    private void createChannelId(String channelId) {
        mChannelId = channelId;
        mNotificationManagerCompat = NotificationManagerCompat.from(mContext);
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(mChannelId, "CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
    }
}
