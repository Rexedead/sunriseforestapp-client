package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.NotificationsPresenter;

public class NotificationsFragment extends NavigatedFragment {

    private static final int ITEM_ON_NAV = 2;
    private static final String TAG = "NotificationsFragment";

    private NotificationsPresenter mPresenter = NotificationsPresenter.getInstance();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notifications_fragment, container, false);
        showBottomNavigation();
        return view;
    }


    @Override
    protected int getItemOnNavigationMenu() {
        return ITEM_ON_NAV;
    }

    @Override
    protected String createTag() {
        return TAG;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }
}
