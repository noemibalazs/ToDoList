package com.example.android.todolist.serv;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.todolist.R;
import com.example.android.todolist.ui.MainActivity;

public class NotificationUtils {

    private static final String INSERT_REMINDER_NOTIFICATION_CHANNEL_ID = "main_notification_channel";
    private static final int PENDING_ID = 21;
    private static final int INSERT_ID = 12;

    public static void newTaskInsertedReminder(Context context){

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(INSERT_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.notification_channel),
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, INSERT_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.materialRed))
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(addPendingIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        }

        manager.notify(INSERT_ID, builder.build());


    }


    private static  Bitmap largeIcon(Context context){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        return bitmap;
    }

    private static PendingIntent addPendingIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, PENDING_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
