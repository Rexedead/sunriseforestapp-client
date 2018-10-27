package pro.sunriseforest.sunriseforestapp_client.presenter;


import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Token;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.server.ServerHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.LoginActivity;

public class LoginPresenter extends AppPresenter<LoginActivity> {

    public static String TAG = "LOGIN_PRESENTER";

    private ServerHelper mServerHelper;
    private SharedPreferenceHelper mPreferenceHelper;


    public LoginPresenter(){
        mServerHelper = ServerHelper.getInstance();
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }
    @Override
    public void update() {
        if(mPreferenceHelper.getToken() != null){
            mActivity.showTaskDeskActivity();
        }
    }

    public void registration(){
        mActivity.showRegistrationActivity();
    }

    public void login(String login, String password){
        try{
            Token token = mServerHelper.getToken(login, password);
            mActivity.showTaskDeskActivity();
            mPreferenceHelper.saveToken(token);
        }catch (IllegalArgumentException e){
            mActivity.showError(e.getMessage());

        }
    }


    @Override
    public String getTAG() {
        return TAG;
    }


}
