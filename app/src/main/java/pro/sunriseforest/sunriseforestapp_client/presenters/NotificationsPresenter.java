package pro.sunriseforest.sunriseforestapp_client.presenters;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
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

    private NotificationsPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }



    @Override
    protected String getTAG() {
        return TAG;
    }
}
