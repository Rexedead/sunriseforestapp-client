package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;

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

        builder.setContentTitle(notification.getHeadline())
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        //todo hz chot: походу на сервере нужно формировать готовый текст уведомления, сохранять в SunriseNotification.msg
        switch (notification.getType()){
            case AMOUNT_OF_TASKS_TYPE:
                String text = "появились новые таски, целых " + notification.getData() + " штук))))";
                builder.setContentText(text);
        }

        return builder.build();
    }

    public void showNotification(SunriseNotification notification){

        //не кидаем уведомление если нет новых тасков
        /* todo а вообще в таких ситуациях нужно с сервера присылать пустую notification с type = 0,
           todo это будет говорить что новых уведомлений для пользователя нет*/
//        if(notification.getType() == AMOUNT_OF_TASKS_TYPE && (int)notification.getData() == 0) return;

        mNotificationManager.notify(123, getNotification(notification));
    }
}
