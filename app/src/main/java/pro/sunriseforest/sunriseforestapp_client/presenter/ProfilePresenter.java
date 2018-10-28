package pro.sunriseforest.sunriseforestapp_client.presenter;

import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.ProfileActivity;


public class ProfilePresenter extends AppPresenter<ProfileActivity> {

    public static String TAG = "PROFILE_PRESENTER";
    private SharedPreferenceHelper mPreferenceHelper;

    public ProfilePresenter() {
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
        mPreferenceHelper.saveToken(null);
        mActivity.showLoginActivity();
    }

    public String getUserLogin() {
        return Objects.requireNonNull(mPreferenceHelper.getToken()).getLogin();
    }

}
