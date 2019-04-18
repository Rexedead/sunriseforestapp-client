package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;

public class SplashFragment extends BaseFragment {


    @Override
    protected String createTag() {
        return "SplashFragment";
    }

    @Override
    protected BasePresenter getPresenter() {

        return new BasePresenter() {
            @Override
            protected String getTAG() {
                return "SplashPresenter";
            }
        };

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null){

        }

    }
}
