package com.heartmeetsoul.visionboard;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotifications(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void showNotifications(String title,String message)
    {
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(getApplicationContext(),"VisionBoard").setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_400)).setSmallIcon(R.drawable.dream).setColorized(true).setContentText(message)
                .setContentTitle(title).setStyle(new NotificationCompat.BigTextStyle().bigText("The Secret of successful people is that they don't keep their dreams in their head they keep them in front of their eyes,they realize it every day and work hard for it.Wanna realize your dreams?"))
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE).setContentIntent(contentIntent(getApplicationContext())).addAction(drinkWaterAction(getApplicationContext())).addAction(ignoreReminderAction(getApplicationContext())).setAutoCancel(true).setLights(22,23,44);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN&&Build.VERSION.SDK_INT<=Build.VERSION_CODES.O)
        {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }


       // NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"VisionBoard").setContentTitle(title).setContentText(message).setAutoCancel(true).setSmallIcon(R.drawable.dream);
        NotificationManagerCompat manager= NotificationManagerCompat.from(this);
        manager.notify(999,notificationBuilder.build());
    }


    public static  NotificationCompat.Action ignoreReminderAction(Context ctx)
    {
        Intent ignoreReminderIntent=new Intent(ctx,SliderShow.class);
        // ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent=PendingIntent.getService(ctx,300,ignoreReminderIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction=new NotificationCompat.Action(R.drawable.ic_launcher_foreground,"No",ignoreReminderPendingIntent);

        return ignoreReminderAction;
    }

    public static NotificationCompat.Action drinkWaterAction(Context ctx)
    {
        Intent drinkWaterIntent=new Intent(ctx,SliderShow.class);
        //drinkWaterIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent drinkWaterPendingIntent=PendingIntent.getService(ctx,100,drinkWaterIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action drinkWaterAction=new NotificationCompat.Action(R.drawable.ic_launcher_foreground,"Yes",drinkWaterPendingIntent);
        return drinkWaterAction;
    }


    private PendingIntent contentIntent(Context applicationContext) {

        Intent intent=new Intent(applicationContext,SliderShow.class);


        return PendingIntent.getActivity(applicationContext,200,intent,PendingIntent.FLAG_UPDATE_CURRENT);

    }
}
