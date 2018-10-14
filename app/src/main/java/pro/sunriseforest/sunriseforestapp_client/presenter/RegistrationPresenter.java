package pro.sunriseforest.sunriseforestapp_client.presenter;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Token;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.server.ServerHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.RegistrationActivity;

public class RegistrationPresenter extends AppPresenter<RegistrationActivity> {

    public static String TAG = "REGISTRATION_PRESENTER";

    private ServerHelper mServerHelper;
    private SharedPreferenceHelper mPreferenceHelper;

    public RegistrationPresenter(){
        mServerHelper = ServerHelper.getInstance();
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }


    @Override
    public void update() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void registration(String login, String password){
        Token token;
        try{
            token = mServerHelper.registration(login, password);
            mPreferenceHelper.saveToken(token);
            mActivity.showTaskDeskActivity();
        }catch (IllegalArgumentException e){
            mActivity.showError(e.getMessage());
        }
    }

}
