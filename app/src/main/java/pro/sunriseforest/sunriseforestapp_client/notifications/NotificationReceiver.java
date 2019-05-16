package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pro.sunriseforest.sunriseforestapp_client.presenters.NotificationsPresenter;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_ACTION = "pro.sunriseforest.sunriseforestapp_client_notification_action";

    private boolean mNotificated;

    private Listener mListener;

    public NotificationReceiver(Listener listener) {
        mListener = listener;
    }

    public NotificationReceiver() {
    }

    //возвращаем true, в том случае, когда не удалось оповестить о том ,что пришли новые уведомления.
    // такое возможно если локальный листенер равен null в тот момент, когда пришло новое уведомление
    public boolean setListener(Listener listener) {
        mListener = listener;

        mNotificated = !mNotificated;

        return mNotificated;
    }

    public void unsetListener(){
        mListener = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(mListener != null){
            mListener.onNotificationReceive();
            mNotificated = true;
        }else{
            mNotificated = false;
        }
    }



    public interface Listener{

        void onNotificationReceive();
    }

}
