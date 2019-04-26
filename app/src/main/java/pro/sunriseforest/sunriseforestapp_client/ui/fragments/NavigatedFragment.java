package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.content.Context;


import pro.sunriseforest.sunriseforestapp_client.ui.AppActivity;

public abstract class NavigatedFragment extends BaseFragment {


    @Override
    public void onStart() {
        super.onStart();
        AppActivity activity = (AppActivity)getActivity();
        int idx = getItemOnNavigationMenu();
        if (activity != null) {
            activity.setCheckedItemMenu(idx);
        }
    }



    protected abstract int getItemOnNavigationMenu();
}
