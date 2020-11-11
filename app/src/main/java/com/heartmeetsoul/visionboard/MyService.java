package com.heartmeetsoul.visionboard;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyService extends JobService {


    private static final int WATER_REMINDER_NOTIFICATION_ID=1138;
    private static final int WATER_REMINDER_PENDING_INTENT_ID=3417;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID=213;
    private static final int DRINK_WATER_PENDING_INTENT_ID=1452;
    BackGroundTask backGroundTask;
    @Override
    public boolean onStartJob(final JobParameters job) {

        backGroundTask=new BackGroundTask()
        {

            @Override
            protected void onPostExecute(String s)
            {
                jobFinished(job,false);
               // Toast.makeText(MyService.this, "Hi", Toast.LENGTH_SHORT).show();
                notifyUser();
            }
        };
        backGroundTask.execute();
        return true;
    }
    private static final String NOTIFICATION_CHANNEL_ID="What's Up";

    private void notifyUser() {


        NotificationManager notificationManager=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {


            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Dreams", NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(getApplicationContext(),NOTIFICATION_CHANNEL_ID).setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue_400)).setSmallIcon(R.drawable.dream).setColorized(true).setContentText("Don't let your dreams fade away..!!")
                .setContentTitle("Reminder").setStyle(new NotificationCompat.BigTextStyle().bigText("The Secret of successful people is that they don't keep their dreams in their head they keep them in front of their eyes,they realize it every day and work hard for it.Wanna realize your dreams?"))
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE).setContentIntent(contentIntent(getApplicationContext())).addAction(drinkWaterAction(getApplicationContext())).addAction(ignoreReminderAction(getApplicationContext())).setAutoCancel(true).setLights(22,23,44);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN&&Build.VERSION.SDK_INT<=Build.VERSION_CODES.O)
        {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID,notificationBuilder.build());

    }

    private PendingIntent contentIntent(Context applicationContext) {

            Intent intent=new Intent(applicationContext,SliderShow.class);


            return PendingIntent.getActivity(applicationContext,WATER_REMINDER_PENDING_INTENT_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static NotificationCompat.Action drinkWaterAction(Context ctx)
    {
        Intent drinkWaterIntent=new Intent(ctx,SliderShow.class);
        //drinkWaterIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent drinkWaterPendingIntent=PendingIntent.getService(ctx,DRINK_WATER_PENDING_INTENT_ID,drinkWaterIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action drinkWaterAction=new NotificationCompat.Action(R.drawable.ic_launcher_foreground,"Yes",drinkWaterPendingIntent);
        return drinkWaterAction;
    }

    public static  NotificationCompat.Action ignoreReminderAction(Context ctx)
    {
        Intent ignoreReminderIntent=new Intent(ctx,SliderShow.class);
       // ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent=PendingIntent.getService(ctx,ACTION_IGNORE_PENDING_INTENT_ID,ignoreReminderIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction=new NotificationCompat.Action(R.drawable.ic_launcher_foreground,"No",ignoreReminderPendingIntent);

        return ignoreReminderAction;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }


    public static class BackGroundTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            return null ;
        }
    }
}
