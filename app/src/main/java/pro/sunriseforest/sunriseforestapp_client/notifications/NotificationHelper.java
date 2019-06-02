package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.ui.AppActivity;

public class NotificationHelper {

    private Context mContext;
    private NotificationManager mNotificationManager;

    public static final int AMOUNT_OF_TASKS_TYPE = 1;

    public static NotificationHelper getInstance(Context context){
        return new NotificationHelper(context);
    }

    public NotificationHelper(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);

    }

    public NotificationCompat.Builder getNotificationBuilder(){

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return new NotificationCompat.Builder(mContext);
        } else {

            String channelId = "sunrise_channel_id";
            if(mNotificationManager.getNotificationChannel(channelId) == null){
                NotificationChannel channel = new NotificationChannel(channelId, "Sunrise", NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }
            return new NotificationCompat.Builder(mContext,channelId);
        }

    }

    public Notification getNotification(SunriseNotification notification){

        NotificationCompat.Builder builder = getNotificationBuilder();

        Intent intent = new Intent(mContext, AppActivity.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(mContext, 2343, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentTitle(notification.getHeadline())
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        switch (notification.getType()){
            case AMOUNT_OF_TASKS_TYPE:
                builder.setContentText("появились новые задания. ");
                break;
            default:
                builder.setContentText("бронь");
        }

        return builder.build();
    }

    public void showNotification(SunriseNotification notification){

        //не кидаем уведомление если нет новых тасков

        mNotificationManager.notify(123, getNotification(notification));
    }
}
