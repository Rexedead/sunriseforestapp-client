package pro.sunriseforest.sunriseforestapp_client.presenters;


import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.ProfileFragment;

public class ProfilePresenter extends BasePresenter<ProfileFragment> {

    private static final String TAG = "ProfilePresenter";

    private static final ProfilePresenter ourInstance = new ProfilePresenter();

    public static ProfilePresenter getInstance() {
        return ourInstance;
    }

    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private ProfilePresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }


    @Override
    public String getTAG() {
        return TAG;
    }


}
