package pro.sunriseforest.sunriseforestapp_client.presenter.old;

import android.support.annotation.NonNull;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class RegistrationPresenter extends AppPresenter<RegistrationActivity> {
//
//    public static String TAG = "REGISTRATION_PRESENTER";
//
//
//    private SharedPreferenceHelper mPreferenceHelper;
//
//    public RegistrationPresenter(){
//        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
//    }
//
//    @Override
//    public void update() {
//
//    }
//
//    @Override
//    public String getTAG() {
//        return TAG;
//    }
//
//    public void selectedRegistration(final User user){
//        Call<User> call = ApiFactory.getSunriseForestService().userRegistration(user);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
//
//                int code = response.code();
//                if(code == 200){
//                    mPreferenceHelper.saveUser(response.body());
//                    mView.showTaskDeskActivity();
//                }else {
//                    showErrorByCode(code);
//                }
//            }
//
//
//            @Override
//            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
//                Log.e("RegistrationPresenter", t.getMessage());
//
//            }
//        });
//
//
//    }
//    private void showErrorByCode(int code){
//        if(code == 401){
//            mView.showError("этот логин уже занят");
//        }else if(code == 400){
//            mView.showError("проблемы на сервере. попробуйте еще раз");
//        }else{
//            mView.showError("проблемы на сервере. Код ошибки " + code);
//        }
//    }
//
//}
