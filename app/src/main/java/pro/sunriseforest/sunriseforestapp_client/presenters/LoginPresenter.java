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
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.LoginFragment;
import retrofit2.HttpException;

public class LoginPresenter extends BasePresenter<LoginFragment>{
    private static final LoginPresenter ourInstance = new LoginPresenter();
    private static final String TAG = "LoginPresenter";
    public static LoginPresenter getInstance() {
        return ourInstance;
    }

    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private LoginPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    public void selectedLogin(User user){
        log(String.format("selectedLogin(user=%s)", user));

        String email = user.getEmail();
        String password = user.getPassword();

        ApiFactory
                .getSunriseForestService()
                .userLoginByEmail(email, password)
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::saveUser,
                        this::handleNetworkError,
                        this::showTasks
                        );

    }
    public void selectedGoToRegistration(){
        mNavigationManager.fromLoginToRegistration();
    }

    private void saveUser(User user){
        log(String.format("saveUser(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }
    private void showTasks(){
        log("showTasks()");
        mNavigationManager.fromLoginToDesk();


    }

    private void showError(String msg){
        log(String.format("showError(String msg = %s)", msg));
        if(!viewIsNullAndLog("showError")){
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


    @Override
    protected String getTAG() {
        return TAG;
    }
}
