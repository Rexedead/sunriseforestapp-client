package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.date.DataBaseHelper;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.SunriseForestService;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.TaskDeskActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDeskPresenter extends AppPresenter<TaskDeskActivity>{

    public static String TAG ="TASK_DESK_PRESENTER";

    private static final int CODE_SUCCESS = 1001;
    private static final int CODE_UNSUCCESS = 1002;

    private SharedPreferenceHelper mPreferenceHelper;


    public TaskDeskPresenter(){
        mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    public void initTaskDeskActivity(TaskDeskActivity activity) {

        String token = mPreferenceHelper.getMyUser().getToken();
        Call<List<Task>> call = ApiFactory.getSunriseForestService().getTasks(token);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> resp) {
                int code = resp.code();

                if(code == 200){
//                    mDataBaseHelper.cacheTasks(resp.body());
                    List<Task> tasks = resp.body();
                    mActivity.showListTask(tasks);
                }else {
                    Log.e("TaskDeskPresenter", resp.message());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("TaskDeskPresenter", t.getMessage());
            }
        });


    }



    public void openSelectedTask(int task_id) {
        Call<List<Task>> call = ApiFactory.getSunriseForestService().getTask(task_id);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> resp) {
                int code = resp.code();

                if(code == 200){
                        List<Task> task = resp.body();
                        mActivity.showSingleTask(task);
//                      mDataBaseHelper.cacheTasks(resp.body());
                }else {
                    Log.e("TaskDeskPresenter", resp.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {
                Log.e("TaskDeskPresenter", t.getMessage());
            }
        });


    }


    public void fabAction(){
       mActivity.createNewTask();
    }

    @Override
    public void update() {

        String token = mPreferenceHelper.getMyUser().getToken();
        Call<List<Task>> call = ApiFactory.getSunriseForestService().getTasks(token);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> resp) {
                int code = resp.code();

                if(code == 200){
                    List<Task> tasks = resp.body();

//                    mDataBaseHelper.cacheTasks(tasks);
                    mActivity.showListTask(tasks);
                }else {
                    Log.e("TaskDeskPresenter", resp.message());
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {
                Log.e("TaskDeskPresenter", t.getMessage());
            }
        });



    }



    @Override
    public String getTAG() {
        return TAG;
    }
}
