package pro.sunriseforest.sunriseforestapp_client.presenter;


import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.ui.IView;

public abstract class BasePresenter<A extends IView> implements IPresenter {
    private String TAG = "%%%/presenter";

    protected A mView;

    protected A getView(){
        return mView;
    }

    public void bindView(A view){
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

    protected void logError(String msg){
        Log.e(TAG,msg);
    }
    protected void log(String msg){
        Log.i(TAG,msg);
    }

}
