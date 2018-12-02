package pro.sunriseforest.sunriseforestapp_client.presenter;


import android.support.annotation.NonNull;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends AppPresenter<LoginActivity> {

    public static String TAG = "LOGIN_PRESENTER";

    private SharedPreferenceHelper mPreferenceHelper;

    LoginPresenter(){
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }
    @Override
    public void update() {
        if(mPreferenceHelper.getMyUser() != null){
            mActivity.showTaskDeskActivity();
        }
    }

    public void registration(){
        mActivity.showRegistrationActivity();
    }

    public void login(String login, String password){

        Call<User> call = ApiFactory.getSunriseForestService().userLoginByEmail(login, password);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                int code = response.code();

                User user = response.body();
                if(user != null){
                    mPreferenceHelper.saveUser(user);
                    mActivity.showTaskDeskActivity();
                }
                showErrorByCode(code);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
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
