package pro.sunriseforest.sunriseforestapp_client.presenter.old;

import android.support.annotation.NonNull;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Client;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
//import pro.sunriseforest.sunriseforestapp_client.ui.NewTaskActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class NewTaskPresenter extends AppPresenter<NewTaskActivity> {
////
////    public static String TAG = "NEW_TASK_PRESENTER";
////
////    private SharedPreferenceHelper mPreferenceHelper;
////
////    public NewTaskPresenter(){
////        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
////    }
////
////    @Override
////    public void update() {
////
////    }
////
////    @Override
////    public String getTAG() {
////        return TAG;
////    }
////
////    public void addTask(final Task task) {
////        Call<Task> call = ApiFactory.getSunriseForestService().addtask(task, mPreferenceHelper.getToken());
////        call.enqueue(new Callback<Task>() {
////            @Override
////            public void onResponse(@NonNull Call<Task> call, @NonNull Response<Task> response) {
////
////                int code = response.code();
////                if (code == 200) {
////                    mView.showTaskDeskActivity();
////                } else if (code == 400) {
////                    mView.showError("проблемы на сервере. попробуйте еще раз");
////                } else {
////                    mView.showError("проблемы на сервере. Код ошибки " + code);
////                }
////
////            }
////
////            @Override
////            public void onFailure(@NonNull Call<Task> call, @NonNull Throwable t) {
////                Log.e("NewTaskPresenter", t.getMessage());
////
////            }
////        });
////    }
////    public void addNewClient(final Client client) {
////        Call<Client> call = ApiFactory.getSunriseForestService().addclient(client, mPreferenceHelper.getToken());
////        call.enqueue(new Callback<Client>() {
////            @Override
////            public void onResponse(@NonNull Call<Client> call, @NonNull Response<Client> response) {
////
////                int code = response.code();
////                if (code == 200) {
////                    mView.showTaskDeskActivity();
////                } else if (code == 400) {
////                    mView.showError("проблемы на сервере. попробуйте еще раз");
////                } else {
////                    mView.showError("проблемы на сервере. Код ошибки " + code);
////                }
////
////            }
////
////            @Override
////            public void onFailure(@NonNull Call<Client> call, @NonNull Throwable t) {
////                Log.e("NewTaskPresenter", t.getMessage());
////
////            }
////        });
////    }
////}


