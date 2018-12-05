package pro.sunriseforest.sunriseforestapp_client.presenter;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.ProfileActivity;


public class ProfilePresenter extends AppPresenter<ProfileActivity> {

    public static String TAG = "PROFILE_PRESENTER";
    private SharedPreferenceHelper mPreferenceHelper;

    ProfilePresenter() {
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    @Override
    public void update() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void exitFromApp() {
        mPreferenceHelper.removeUser();
        mActivity.showLoginActivity();
    }


}
