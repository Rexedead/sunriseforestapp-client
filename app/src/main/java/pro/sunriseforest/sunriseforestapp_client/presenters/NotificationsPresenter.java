package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.notifications.JobSchedulerHelper;
import pro.sunriseforest.sunriseforestapp_client.notifications.SunriseNotificationsProvider;
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

    // ссылка по итогу будет так же храниться в адаптере.
    private List<SunriseNotification> mSunriseNotifications;


    private NotificationsPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());

        mSunriseNotifications = SunriseNotificationsProvider
                .getInstance(SunriseForestApp.getAppContext())
                .getNotifications();


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
                mSunriseNotifications.get(position).getHeadline()));

    }

    //todo временный метод
    public void clickedTurnNotification() {
        mSharedPreferenceHelper.updateSettings(settings ->{
                    boolean areWorks =  settings.turnNotifications();
                    turnNotification(areWorks);
                });

        log(" clickedTurnNotification()");

    }

    private void turnNotification(boolean works){
        getView().showNotificationsAreWorks(works);
        JobSchedulerHelper jobSchedulerHelper =
                new JobSchedulerHelper(SunriseForestApp.getAppContext());

        if(works) jobSchedulerHelper.startNotificationJob();
        else jobSchedulerHelper.cancelNotificationJob();
    }
    private void updateNotifications(){
        log("updateNotifications()");

        mSunriseNotifications = SunriseNotificationsProvider
                .getInstance(SunriseForestApp.getAppContext())
                .getNotifications();

        mView.updateNotifications(mSunriseNotifications);
        mNotificationsIsUpdated = true;
    }



    public boolean notificationsAreWorks() {
        return mSharedPreferenceHelper.getSettings().isNotificationsAreWorks();
    }
}
