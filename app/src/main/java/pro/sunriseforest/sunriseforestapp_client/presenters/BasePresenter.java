package pro.sunriseforest.sunriseforestapp_client.presenters;


import android.support.v4.app.Fragment;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.net.ErrorMassageManager;
import pro.sunriseforest.sunriseforestapp_client.utils.NetworkUtils;
import pro.sunriseforest.sunriseforestapp_client.ui.AppActivity;
import retrofit2.HttpException;

public abstract class BasePresenter <F extends Fragment> {
    public final String TAG = "%%%/presenter/" + createTAG();

    private boolean mIsFirst = true;

    protected abstract String createTAG();
//
    protected F mView;

    protected F getView(){
        return mView;
    }

    public void bindView(F view){
        log(String.format("bindView(view=%s)", view));
        mView = view;
    }

    public void unBindView(){
        log("unBindView()");
        mIsFirst = false;
        mView = null;
    }

    boolean isViewUnBunding(){
        return mView == null;
    }


    public void cameNewNotifications(List<SunriseNotification> notifications){
        log("cameNewNotifications");
    }

    protected void logError(String msg){
        Log.e(TAG,msg);
    }
    protected void log(String msg){
        Log.i(TAG,msg);
    }

    protected void log(String msg, Object... args){
        log(String.format(msg, args));
    }


    void handleNetworkError(Throwable e) {
        if (e instanceof ConnectException) {
            if(NetworkUtils.isNetworkAvailable()){
                showNetworkError("Проблема с подключением к серверу." +
                        " Связь с сервером востановится через некоторое время");
            }else{
                showNetworkError("Отсутствует подключение к интернету");
            }

        } else if (e instanceof SocketTimeoutException) {
            showNetworkError("На сервере проблема, попробуйте еще раз через пару минут");
        } else if (e instanceof HttpException) {
            showNetworkError(ErrorMassageManager.WhatIsMyError(((HttpException) e).code(),TAG));
        }
        logError(e.getMessage());
    }

    boolean isFirst() {
        return mIsFirst;
    }

    private void showNetworkError(String s) {
        AppActivity activity = (AppActivity) getView().getActivity();
        if(activity != null)  activity.showInfoMessage(s);
    }
}
