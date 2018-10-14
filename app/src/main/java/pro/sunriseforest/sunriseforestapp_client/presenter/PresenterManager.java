package pro.sunriseforest.sunriseforestapp_client.presenter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PresenterManager {

    private static PresenterManager sPresenterManager;

    private List<AppPresenter> mPresenters;

    public static PresenterManager getInstance(){
        if(sPresenterManager == null){
            sPresenterManager = new PresenterManager();
        }
        return sPresenterManager;
    }

    private PresenterManager(){
        mPresenters = new ArrayList<>();
        mPresenters.add(new LoginPresenter());
        mPresenters.add(new RegistrationPresenter());
    }

    public <T extends AppPresenter> T getPresenter (){
        T presenter;
        for (AppPresenter _presenter : mPresenters){
            try {
                presenter = (T)_presenter;
                return presenter;
            } catch (Exception e){

            }
        }
        return null;
    }
}
