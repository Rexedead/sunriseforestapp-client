package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.ErrorMassageManager;
import pro.sunriseforest.sunriseforestapp_client.options.ISharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.IView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppPresenter extends BasePresenter<IView> {

    private static AppPresenter sAppPresenter;

    private ISharedPreferenceHelper mPreferenceHelper;

    private List<Task> mTasks;

    public static AppPresenter getInstance(){
        if(sAppPresenter == null){
            sAppPresenter = new AppPresenter();
        }
        return sAppPresenter;
    }


    private AppPresenter(){
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    public void startApp() {
        log("startApp()");
        User user = mPreferenceHelper.getUser();
        final IView view = getView();
        if(user == null){
            view.showLoginScreen();
        }else{
            view.showDeskScreen();
//            ApiFactory.getSunriseForestService().getTasks(mPreferenceHelper.getToken()).enqueue(new Callback<List<Task>>() {
//                @Override
//                public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {
//                    log("network startApp(): onResponse(), response=" + response);
//                     int code = response.code();
//                     if(code == 200){
//                         view.showDeskScreen(response.body());
//                     }
//
//                    String msg = ErrorMassageManager.LOGIN(code);
//                    if(!TextUtils.isEmpty(msg)) getView().showError(msg);
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {
//                    logError( "network startApp(): onFailure(), throwable=" + t.getMessage());
//                }
//            });
        }
    }

    @Override
    public void selectedLogin(User user) {
        log(String.format("selectedLogin(user=%s)", user));

        String email = user.getEmail();
        String password = user.getPassword();
        Call<User> call = ApiFactory.getSunriseForestService().userLoginByEmail(email, password);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                log("network selectedLogin: onResponse(), response=" + response);

                int code = response.code();

                User user = response.body();
                if (user != null) {
                    mPreferenceHelper.saveUser(user);
                    mView.showDeskScreen();
                }

                String msg = ErrorMassageManager.LOGIN(code);
                if(!TextUtils.isEmpty(msg)) getView().showError(msg);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                logError( "network selectedLogin: onFailure(), throwable=" + t.getMessage());
            }
        });
    }

    @Override
    public void selectedRegistration(User user) {
        log(String.format("selectedRegistration(user=%s)", user));

        Call<User> call = ApiFactory.getSunriseForestService().userRegistration(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                int code = response.code();
                if(code == 200){
                    mPreferenceHelper.saveUser(response.body());
                    mView.showDeskScreen();
                }else {

                    String msg = ErrorMassageManager.LOGIN(code);
                    if(!TextUtils.isEmpty(msg)) mView.showError(msg);
                }
            }


            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                logError( "network selectedRegistration: onFailure(), throwable=" + t.getMessage());
                mView.showError("ошибка сети");
            }
        });


    }

    public void selectedGotoRegistration(){
        log("selectedGotoRegistration()");
        mView.showRegistrationScreen();
    }

    public void selectedTask(int position){
        log(String.format("selectedTask(position=%s)",position));

    }



    @Override
    public void exitProfile() {
        log("exitProfile()");
    }

    @Override
    public void clickedBack() {
        log("clickedBack()");
    }

}
