package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.util.Log;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.date.DataBaseHelper;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.server.ServerHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.TaskDeskActivity;

public class TaskDeskPresenter extends AppPresenter<TaskDeskActivity>{

    public static String TAG ="TASK_DESK_PRESENTER";

    private DataBaseHelper mDataBaseHelper;
    private ServerHelper mServerHelper;

    public TaskDeskPresenter(){

        mDataBaseHelper = DataBaseHelper.getInstance();
        mServerHelper = ServerHelper.getInstance();

    }

    public void initTaskDeskActivity(TaskDeskActivity activity) {
        //????????
//        mTaskDeskActivityWeakReference = new WeakReference<>(activity);

        List<Task> tasks = mServerHelper.getTasks();
        if(tasks == null || tasks.isEmpty()){
            Log.e("TaskDeskPresenter","СерверХелпер отдал null или пустой лист тасков");
        }
        mDataBaseHelper.cacheTasks(tasks);
//        mTaskDeskActivityWeakReference.get().showListTask(tasks);
    }

    @Override
    public void update() {
        List<Task> tasks = mServerHelper.getTasks();
        if(tasks == null || tasks.isEmpty()){
            Log.e("TaskDeskPresenter","СерверХелпер отдал null или пустой лист тасков");
        }
        mDataBaseHelper.cacheTasks(tasks);
        mActivity.showListTask(tasks);

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
