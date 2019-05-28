package pro.sunriseforest.sunriseforestapp_client.presenters;


import android.support.v4.app.Fragment;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import pro.sunriseforest.sunriseforestapp_client.net.ErrorMassageManager;
import pro.sunriseforest.sunriseforestapp_client.net.Utils;
import pro.sunriseforest.sunriseforestapp_client.ui.AppActivity;
import retrofit2.HttpException;

public abstract class BasePresenter <F extends Fragment> {
    public final String TAG = "%%%/presenter/" + createTAG();


    protected abstract String createTAG();
//
    protected F mView;

    protected F getView(){
        return mView;
    }

    public void bindView(F view){
        log(String.format("bindView(view=%s)", view));
        mView = view;
        AppActivity activity =  (AppActivity)mView.getActivity();

        if(activity != null){
            boolean newNotificationsCameLater =  activity
                    .setNewNotificationsCameListener(this::cameNewNotifications);

            if(newNotificationsCameLater) cameNewNotifications();
        }
    }

    public void unBindView(){
        log("unBindView()");
        mView = null;
    }

    public boolean isViewUnBunding(){
        return mView == null;
    }

    protected boolean viewIsNullAndLog(String nameMethod){
        if (getView() == null) {
            log(nameMethod + ": view is null");
            return true;
        }else{
            return false;
        }

    }

    protected void cameNewNotifications(){
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
            if(Utils.isNetworkAvailable()){
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

    protected void showNetworkError(String s) {
        ((AppActivity) getView().getActivity()).showInfoMessage(s);
    }
}
