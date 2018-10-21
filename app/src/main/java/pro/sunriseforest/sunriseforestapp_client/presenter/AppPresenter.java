package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.support.v7.app.AppCompatActivity;

public abstract class AppPresenter<A extends AppCompatActivity> {

    protected A mActivity;

    protected A getActivity(){
        return mActivity;
    }

    public void bindActivity(A activity){
        mActivity = activity;
    }

    public void unBindActivity(){
        mActivity = null;
    }

    public boolean isActivityUnBunding(){
        return mActivity == null;
    }
    public abstract void update();
    public abstract String getTAG();

}
