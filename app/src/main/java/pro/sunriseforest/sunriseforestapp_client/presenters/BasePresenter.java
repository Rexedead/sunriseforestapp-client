package pro.sunriseforest.sunriseforestapp_client.presenters;


import android.support.v4.app.Fragment;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import pro.sunriseforest.sunriseforestapp_client.net.ErrorMassageManager;
import pro.sunriseforest.sunriseforestapp_client.ui.AppActivity;
import retrofit2.HttpException;

public abstract class BasePresenter <F extends Fragment> {
    private String TAG = "%%%/presenter/" + getTAG();


    protected abstract String getTAG();
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

    protected void logError(String msg){
        Log.e(TAG,msg);
    }
    protected void log(String msg){
        Log.i(TAG,msg);
    }

    protected void log(String msg, Object... args){
        log(String.format(msg, args));
    }

    //todo чет неоч
    void handleNetworkError(Throwable e) {
        if (e instanceof ConnectException) {
            ((AppActivity)getView().getActivity()).showInfoMessage("Отсутствует подключение к интернету");
        } else if (e instanceof SocketTimeoutException) {
            ((AppActivity)getView().getActivity()).showInfoMessage("На сервере проблема, попробуйте еще раз через пару минут");
        } else if (e instanceof HttpException) {
            ((AppActivity)getView().getActivity()).showInfoMessage(ErrorMassageManager.WhatIsMyError(((HttpException) e).code(),TAG));
        }
        logError(e.getMessage());
    }
}
