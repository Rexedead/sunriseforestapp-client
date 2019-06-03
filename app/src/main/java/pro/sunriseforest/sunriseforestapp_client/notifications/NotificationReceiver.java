package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.presenters.DeskPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.LoginPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.NewTaskPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.NotificationsPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.TaskPresenter;

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
    public void setListener(Listener listener) {
        mListener = listener;

        if(!mNotificated) {
            mListener.onNotificationReceive();
            mNotificated = true;
        }

    }

    public void unsetListener(){
        mListener = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("%%%/receiver", "onReceive: ");

        if(mListener != null){
            mListener.onNotificationReceive();
            mNotificated = true;
        }else{
            mNotificated = false;
        }

        notifyPresenters();
    }


    public void notifyPresenters(){
        DeskPresenter.getInstance().cameNewNotifications(null);
        TaskPresenter.getInstance().cameNewNotifications(null);
        NotificationsPresenter.getInstance().cameNewNotifications(null);
    }


    public interface Listener{

        void onNotificationReceive();
    }

}
