package pro.sunriseforest.sunriseforestapp_client.presenters;


import android.text.Editable;
import android.text.TextWatcher;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.ProfileFragment;

public class ProfilePresenter extends BasePresenter<ProfileFragment> implements TextWatcher {

    private static final String TAG = "ProfilePresenter";
    private User mUser;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private NavigationManager mNavigationManager;

    private static final ProfilePresenter ourInstance = new ProfilePresenter();

    public static ProfilePresenter getInstance() {
        return ourInstance;
    }


    private ProfilePresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
        mUser = mSharedPreferenceHelper.getUser();
    }


    @Override
    public void bindView(ProfileFragment view) {
        super.bindView(view);
        view.showProfile(mUser);

        boolean yes = canChangeProfile();
        mView.setEnabledEditTexts(yes);
        mView.saveIsVisible(false);


    }

    public void clickedSaveButton(){
        log("clickedSaveButton()");
        //...
        mView.showToast("*клик по сохранялке*");
        saveProfile();
        mView.saveIsVisible(false);


    }

    private void saveProfile() {
        log("saveProfile()");
    }


    private boolean canChangeProfile(){
        log("canChangeProfile()");
        //...этот метод походу уже и не нужен, если все изначально можно редактировать
        return true;
    }




    @Override
    public String getTAG() {
        return TAG;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String changedText = editable.toString();
            log("afterTextChanged: "+changedText);
            mView.saveIsVisible(true);
    }
}

