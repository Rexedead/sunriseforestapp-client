package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Notification;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.NotificationsFragment;

public class NotificationsPresenter extends BasePresenter<NotificationsFragment> {
    private static final String TAG = "NotificationPresenter";


    private static final NotificationsPresenter ourInstance = new NotificationsPresenter();

    public static NotificationsPresenter getInstance() {
        return ourInstance;
    }

    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private boolean mNotificationsIsUpdated = false;

    private List<Notification> mNotifications;


    private NotificationsPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());

        mNotifications = new ArrayList<>();
        mNotifications.add(new Notification("Заголовок 1"));
        mNotifications.add(new Notification("Заголовок 2"));
        mNotifications.add(new Notification("Заголовок 3"));
    }


    @Override
    public void bindView(NotificationsFragment view) {
        super.bindView(view);

        if(!mNotificationsIsUpdated){
            updateNotifications();
        }
    }


    @Override
    protected String getTAG() {
        return TAG;
    }

    public void clickedSelectedNotification(int position) {
        log(" clickedSelectedNotification(position = %s)" , position);

        getView().showToast(String.format("*Клик по уведомлению с заголовком \"%s\" *",
                mNotifications.get(position).getHeadline()));

    }

    //todo временный метод
    public void clickedTurnNotification() {
        log(" clickedTurnNotification()");
        mSharedPreferenceHelper.updateSettings(settings ->
                getView().showNotificationsAreWorks( settings.turnNotifications()));

    }

    private void updateNotifications(){
        log("updateNotifications()");
        mView.updateNotifications(mNotifications);
        mNotificationsIsUpdated = true;
    }


    public boolean notificationsAreWorks() {
        return mSharedPreferenceHelper.getSettings().isNotificationsAreWorks();
    }
}
