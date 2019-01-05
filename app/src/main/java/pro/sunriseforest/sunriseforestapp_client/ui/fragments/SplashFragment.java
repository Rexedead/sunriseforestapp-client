package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import pro.sunriseforest.sunriseforestapp_client.presenter.AppPresenter;

public class SplashFragment extends LogFragment {


    @Override
    protected String createTag() {
        return "SplashFragment";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null){
            AppPresenter.getInstance().startApp();
        }

    }
}
