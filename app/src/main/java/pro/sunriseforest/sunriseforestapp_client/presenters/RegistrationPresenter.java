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
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.RegistrationFragment;
import retrofit2.HttpException;

public class RegistrationPresenter extends BasePresenter<RegistrationFragment> {

    private static final RegistrationPresenter ourInstance = new RegistrationPresenter();
    private static final String TAG = "RegistrationPresenter";
    public static RegistrationPresenter getInstance() {
        return ourInstance;
    }

    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private RegistrationPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void selectedRegistration(final User user) {
        log(String.format("selectedRegistration"));

        ApiFactory
                .getSunriseForestService()
                .userRegistration(user)
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::saveUser,
                        this::handleNetworkError,
                        this::showTasks
                );


    }

    private void saveUser(User user) {
        log(String.format("saveUser(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }

    private void showTasks() {
        log("showTasks()");
        mNavigationManager.fromRegistrationToDesk();


    }

    private void showError(String msg) {
        log(String.format("showError(String msg = %s)", msg));
        if (getView() == null) {
            log("showError() : view is null");
        } else {
            getView().showError(msg);
        }
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
