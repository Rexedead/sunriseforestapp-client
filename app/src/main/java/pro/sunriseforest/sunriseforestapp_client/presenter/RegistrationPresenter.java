package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.util.Log;

import java.io.IOException;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Contractor;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.RegistrationActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPresenter extends AppPresenter<RegistrationActivity> {

    public static String TAG = "REGISTRATION_PRESENTER";

    private static final int CODE_SUCCESS = 1001;
    private static final int CODE_UNSUCCESS = 1002;
    private static final int CODE_LOGIN_IS_BUSY = 1101;


    private SharedPreferenceHelper mPreferenceHelper;

    public RegistrationPresenter(){
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    @Override
    public void update() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void registration(final Contractor contractor){
        Call<Contractor> call = ApiFactory.getSunriseForestService().registration(contractor);
        call.enqueue(new Callback<Contractor>() {
            @Override
            public void onResponse(Call<Contractor> call, Response<Contractor> response) {

                int code = response.code();
                if(code == 200){
                    mPreferenceHelper.saveContractor(response.body());
                    mActivity.showTaskDeskActivity();
                }else if(code == 401){
                    mActivity.showError("этот логин уже занят");
                }else if(code == 400){
                    mActivity.showError("проблемы на сервере. попробуйте еще раз");
                }else{
                    mActivity.showError("проблемы на сервере. Код ошибки " + code);
                }

            }

            @Override
            public void onFailure(Call<Contractor> call, Throwable t) {
                Log.e("RegistrationPresenter", t.getMessage());

            }
        });



    }

}
