package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


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
