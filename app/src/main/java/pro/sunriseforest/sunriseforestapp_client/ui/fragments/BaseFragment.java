package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.ui.IView;

public abstract class BaseFragment extends Fragment {


    protected abstract String createTag();
    protected abstract BasePresenter getPresenter();

    private final String mTAG = "%%%/fragments/" + createTag();




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("lc: onCreate()");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("lc: onAttach()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("lc: onActivityCreated()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        hideBottomNavigation();
    }

    @Override
    public void onStart() {
        super.onStart();
        log("lc: onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("lc: onResume()");
        BasePresenter presenter = getPresenter();
        presenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        log("lc: onPause()");
        getPresenter().unBindView();
    }

    @Override
    public void onStop() {
        super.onStop();
        log("lc: onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("lc: onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("lc: onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("lc: onDetach()");
    }

    protected void showBottomNavigation(){
        log("showBottomNavigation()");
        IView activity = (IView)getActivity();
        if(activity != null){
            activity.showBottomNavigation();
        }
    }

    protected void hideBottomNavigation(){
        log("hideBottomNavigation()");
        IView activity = (IView)getActivity();
        if(activity!=null){
            activity.hideBottomNavigation();
        }
    }
    public void showError(String msg){
        showToast(msg);
    }

    public void showToast(String msg){
        Toast.makeText(SunriseForestApp.getAppContext(), msg, Toast.LENGTH_LONG).show();

    }

    protected void log(String msg){
        Log.i( mTAG, msg);
    }
    protected void log(String msg, Object... args){
        log(String.format(msg, args));
    }
    protected void logError(String msg){
        Log.e(mTAG, msg);
    }

}
