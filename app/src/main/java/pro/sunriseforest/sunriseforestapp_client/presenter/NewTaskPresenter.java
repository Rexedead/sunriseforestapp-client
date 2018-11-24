package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.ui.NewTaskActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTaskPresenter extends AppPresenter<NewTaskActivity> {

    public static String TAG = "NEW_TASK_PRESENTER";


    @Override
    public void update() {

    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void addNewTask(final Task task) {
        Call<Task> call = ApiFactory.getSunriseForestService().addtask(task);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NonNull Call<Task> call, @NonNull Response<Task> response) {

                int code = response.code();
                if (code == 200) {
                    mActivity.showTaskDeskActivity();
                } else if (code == 400) {
                    mActivity.showError("проблемы на сервере. попробуйте еще раз");
                } else {
                    mActivity.showError("проблемы на сервере. Код ошибки " + code);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Task> call, @NonNull Throwable t) {
                Log.e("RegistrationPresenter", t.getMessage());

            }
        })
        ;
    }
}


