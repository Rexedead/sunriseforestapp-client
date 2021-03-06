package pro.sunriseforest.sunriseforestapp_client.presenters;


import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.ProfileFragment;

//todo exit from presenters
public class ProfilePresenter extends BasePresenter<ProfileFragment> {

    private User mUser;
    private SharedPreferenceHelper mSharedPreferenceHelper;
    private NavigationManager mNavigationManager;
    private boolean mIsLoading;
    private boolean profileChanged = false;
    private User tempUser;
    private static final ProfilePresenter ourInstance = new ProfilePresenter();
    private int numOfFieldsFirstBind = 3;
    public static ProfilePresenter getInstance() {
        return ourInstance;
    }


    @Override
    public String createTAG() {
        return "ProfilePresenter";
    }

    @Override
    public void bindView(ProfileFragment view) {
        super.bindView(view);
        if (isFirst()) {
            refreshUserData();
        } else updateViewFromPreference();

    }

    private ProfilePresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());


    }

    private void tryUpdateView() {
        log("tryUpdateView()");
        if (mView == null) return;
        if (mIsLoading) mView.showLoading();
        else mView.hideLoading();

        if (profileChanged) {
            mView.showProfile(tempUser);
            mView.showSaveViews();
        } else {
            mView.hideSaveViews();
            mView.showProfile(mUser);
            profileChanged = false;
        }

    }


    private void updateViewFromPreference() {
        mUser = mSharedPreferenceHelper.getUser();
        tryUpdateView();
    }


    /**
     * UI features
     */
    public void refresh() {
        refreshUserData();
    }

    public void clickedExitProfile() {
        log("clickedExitProfile()");
        exitProfile();
    }

    public void clickedSaveButton() {
        log("clickedSaveButton()");
        updateUserData(mUser);
        buttonsAction();
    }

    public void clickedCancelChangesButton() {
        log("clickedCancelChangesButton()");
        mView.showProfile(mSharedPreferenceHelper.getUser());
        buttonsAction();
    }

    private void buttonsAction() {
        profileChanged = false;
        mView.hideSaveViews();
    }

    private void exitProfile() {
        log("exitProfile()");
        TaskPresenter.getInstance().clearChangedTasks();
        mSharedPreferenceHelper.removeUser();
        mNavigationManager.fromProfileToLogin();
    }

    private void startLoading() {
        mIsLoading = true;
        if (mView != null) mView.showLoading();
    }

    private void stopLoading() {
        mIsLoading = false;
        if (mView != null) mView.hideLoading();
    }

    public void descriptionProfileIsChanged() {
        log("descriptionTaskIsChanged()");

        if (isFirst()) {
            numOfFieldsFirstBind--;
        }else numOfFieldsFirstBind=0;

        if (numOfFieldsFirstBind <= 0
                && (!mUser.getEmail().equals(mView.getUserMail()))
                || (!mUser.getPhoneNumber().equals(mView.getUserPhone()))) {
            profileChanged = true;
            tempUser = mUser;
            tempUser.setPhoneNumber(mView.getUserPhone());
            tempUser.setEmail(mView.getUserMail());
            mView.showSaveViews();
        }

    }


    /**
     * Network
     * GET method for force update or first bind
     **/
    private void refreshUserData() {
        log("refreshUserData()");
        startLoading();
        ApiFactory
                .getSunriseForestService()
                .getUser(
                        Integer.parseInt(Objects.requireNonNull(mSharedPreferenceHelper.getUser()).getId()))
                .compose(new AsyncNetTransformer<>())
                .subscribe(u -> {
                            mUser = u;
                            mSharedPreferenceHelper.saveUser(u);
                            stopLoading();
                        },
                        throwable -> {
                            handleNetworkError(throwable);
                            stopLoading();
                            updateViewFromPreference();
                        },
                        this::tryUpdateView);
    }


    /**
     * Network
     * PATCH method for profile
     *
     * @param user the changed user in UI
     */
    private void updateUserData(User user) {
        log("updateUserData()" + user);
        user.setEmail(mView.getUserMail());
        user.setPhoneNumber(mView.getUserPhone());
        ApiFactory
                .getSunriseForestService()
                .updProfile(user.getId(), user)
                .compose(new AsyncNetTransformer<>())
                .subscribe(u -> {
                            mUser = u;
                            mSharedPreferenceHelper.saveUser(u);
                            if (mView != null) mView.showToast("Изменения сохранены");
                            stopLoading();
                        },
                        throwable -> {
                            handleNetworkError(throwable);
                            stopLoading();
                        },

                        this::tryUpdateView);

    }


}

