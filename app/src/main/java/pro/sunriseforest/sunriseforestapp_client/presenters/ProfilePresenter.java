package pro.sunriseforest.sunriseforestapp_client.presenters;


import java.net.ConnectException;
import java.net.SocketTimeoutException;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.net.ErrorMassageManager;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.ProfileFragment;
import retrofit2.HttpException;

public class ProfilePresenter extends BasePresenter<ProfileFragment> {

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
        mView.addListenersForEditText();
        mView.saveIsVisible(false);
        getUserStatistics();

    }

    private void getUserStatistics() {
        log("getUserStatistics()");

        ApiFactory
                .getSunriseForestService()
                .getProfileInfo(mUser.getId())
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setProfileStatistic,this::handleNetworkError);
    }

    private void setProfileStatistic(User user) {
        log("setProfileStatistic()");
        mUser.setTasksCount(user.getTasksCount());
        mUser.setRewardSum(user.getRewardSum());
        mView.setStats(mUser);
    }


    public void clickedExitProfile() {
        log("clickedExitProfile()");
        exitProfile();
    }

    public void clickedSaveButton() {
        log("clickedSaveButton()");
        //...
        mView.showToast("*клик по сохранялке*");
        mUser.setName(mView.getUserNameEditText());
        mUser.setEmail(mView.getUserMailEditText());
        mUser.setPhoneNumber(mView.getUserPhoneEditText());
        saveProfileToServer(mUser);
        mView.saveIsVisible(false);


    }

    private void saveProfileToServer(User user) {
        log("saveProfileToServer()"+user);

         ApiFactory
                .getSunriseForestService()
                .updProfile(user.getId(), user)
                .compose(new AsyncNetTransformer<>())
                 .subscribe(this::saveProfileIntoPreference,this::handleNetworkError);
    }

    private void saveProfileIntoPreference(User user){
        log(String.format("saveProfileIntoPreference(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }

    private void exitProfile() {
        log("exitProfile()");
        mSharedPreferenceHelper.removeUser();
        mNavigationManager.fromProfileToLogin();
    }


    private boolean canChangeProfile() {
        log("canChangeProfile()");
        //...этот метод походу уже и не нужен, если все изначально можно редактировать
        return true;
    }


    @Override
    public String getTAG() {
        return TAG;
    }


    private void handleNetworkError(Throwable e) {
        if (e instanceof ConnectException) {
            mView.showToast("Отсутствует подключение к интернету");
        } else if (e instanceof SocketTimeoutException) {
            mView.showToast("На сервере проблема, попробуйте еще раз через пару минут");
        } else if (e instanceof HttpException) {
            mView.showToast(ErrorMassageManager.WhatIsMyError(((HttpException) e).code(),TAG));
        }
        logError(e.getMessage());
    }
}

