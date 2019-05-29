package pro.sunriseforest.sunriseforestapp_client.ui.fragments;



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
        showBottomNavigation();
    }

    protected abstract int getItemOnNavigationMenu();
}
