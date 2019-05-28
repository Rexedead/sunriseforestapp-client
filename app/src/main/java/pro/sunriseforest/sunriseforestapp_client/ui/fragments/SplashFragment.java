package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            protected String createTAG() {
                return "SplashPresenter";
            }
        };

    }

}
