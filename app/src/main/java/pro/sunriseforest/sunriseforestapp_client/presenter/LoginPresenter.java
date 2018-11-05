package pro.sunriseforest.sunriseforestapp_client.presenter;


import android.util.Log;

import java.io.IOException;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Contractor;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends AppPresenter<LoginActivity> {

    public static String TAG = "LOGIN_PRESENTER";

    private static final int CODE_WRONG_PASSWORD = 1102;
    private static final int CODE_WRONG_LOGIN = 1103;

    private SharedPreferenceHelper mPreferenceHelper;


    public LoginPresenter(){
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }
    @Override
    public void update() {
        if(mPreferenceHelper.getMyConractor() != null){
            mActivity.showTaskDeskActivity();
        }
    }

    public void registration(){
        mActivity.showRegistrationActivity();
    }

    public void login(String login, String password){

            Call<Contractor> call = ApiFactory.getSunriseForestService().login(login, password);
            call.enqueue(new Callback<Contractor>() {

                @Override
                public void onResponse(Call<Contractor> call, Response<Contractor> response) {

                    int code = response.code();
                    Contractor contractor = response.body();
                    if(contractor != null){
                        mPreferenceHelper.saveContractor(contractor);
                        mActivity.showTaskDeskActivity();
                    }
                    showErrorByCode(code);

                }

                @Override
                public void onFailure(Call<Contractor> call, Throwable t) {
                    Log.e("LoginPresenter", t.getMessage());
                }
            });


    }

    private void showErrorByCode(int code){
        if(code == 401){
            mActivity.showError("неверный логин или пароль");
        }else if(code == 400){
            mActivity.showError("запрос не ок. код ошибки 400");
        }
    }


    @Override
    public String getTAG() {
        return TAG;
    }


}
