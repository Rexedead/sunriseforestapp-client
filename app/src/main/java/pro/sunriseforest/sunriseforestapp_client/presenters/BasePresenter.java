package pro.sunriseforest.sunriseforestapp_client.presenters;


import android.support.v4.app.Fragment;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.ui.IView;

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


}
