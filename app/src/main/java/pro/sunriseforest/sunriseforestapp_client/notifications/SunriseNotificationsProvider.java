package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;

public class SunriseNotificationsProvider {

    private final SharedPreferenceHelper mSharedPreferenceHelper;
    private Context mContext;

    public SunriseNotificationsProvider(Context context) {
        mContext = context;
        mSharedPreferenceHelper = SharedPreferenceHelper.getInstance(context);
    }

    public static SunriseNotificationsProvider getInstance(Context context){
        return new SunriseNotificationsProvider(context);
    }

    public List<SunriseNotification> getNotifications(){
        List<SunriseNotification> notifications = mSharedPreferenceHelper.getNotifications();

        if(notifications == null) return new ArrayList<>();

        return notifications ;

    }

    public void append(SunriseNotification notification){
        List<SunriseNotification> newNotifications = mSharedPreferenceHelper.getNotifications();
        newNotifications.add(notification);
        mSharedPreferenceHelper.saveNotifications(newNotifications);

    }

    public void append(List<SunriseNotification> notifications){
        List<SunriseNotification> newNotifications = mSharedPreferenceHelper.getNotifications();

        if(newNotifications == null){
            newNotifications = new ArrayList<>();
        }

        newNotifications.addAll(notifications);
        mSharedPreferenceHelper.saveNotifications(newNotifications);
    }

    public void saveNotifications(List<SunriseNotification> newList){
        mSharedPreferenceHelper.saveNotifications(newList);
    }



}
