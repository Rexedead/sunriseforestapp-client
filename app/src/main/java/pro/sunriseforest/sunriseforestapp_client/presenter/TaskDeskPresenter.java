package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.util.Log;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.date.DataBaseHelper;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.ui.TaskDeskActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDeskPresenter extends AppPresenter<TaskDeskActivity>{

    public static String TAG ="TASK_DESK_PRESENTER";

    private static final int CODE_SUCCESS = 1001;
    private static final int CODE_UNSUCCESS = 1002;

    private DataBaseHelper mDataBaseHelper;

    public TaskDeskPresenter(){

        mDataBaseHelper = DataBaseHelper.getInstance();

    }

    public void initTaskDeskActivity(TaskDeskActivity activity) {
        Call<List<Task>> call = ApiFactory.getSunriseForestService().getTasks();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> resp) {
                int code = resp.code();

                if(code == 200){
                    mDataBaseHelper.cacheTasks(resp.body());
                }else {
                    Log.e("RegistrationPresenter", resp.message());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("RegistrationPresenter", t.getMessage());
            }
        });


    }

    @Override
    public void update() {


        Call<List<Task>> call = ApiFactory.getSunriseForestService().getTasks();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> resp) {
                int code = resp.code();

                if(code == 200){
                    List<Task> tasks = resp.body();

                    mDataBaseHelper.cacheTasks(tasks);
                    mActivity.showListTask(tasks);
                }else {
                    Log.e("RegistrationPresenter", resp.message());
                }

            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("RegistrationPresenter", t.getMessage());
            }
        });



    }


    @Override
    public String getTAG() {
        return TAG;
    }
}
