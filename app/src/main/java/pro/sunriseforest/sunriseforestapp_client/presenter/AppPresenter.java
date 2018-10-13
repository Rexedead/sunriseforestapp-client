package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.date.DataBaseHelper;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.server.ServerHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.TaskDeskActivity;

public class AppPresenter {

    private static AppPresenter sInstance;
    private DataBaseHelper mDataBaseHelper;
    private ServerHelper mServerHelper;

    private WeakReference<TaskDeskActivity> mTaskDeskActivityWeakReference;

    public static AppPresenter getInstance(){
        if(sInstance == null){
            sInstance = new AppPresenter();
        }
        return sInstance;
    }

    private AppPresenter(){
        mDataBaseHelper = DataBaseHelper.getInstance();
        mServerHelper = ServerHelper.getInstance();

    }

    public void initApp(TaskDeskActivity activity) {
        mTaskDeskActivityWeakReference = new WeakReference<>(activity);

        List<Task> tasks = mServerHelper.getTasks();
        if(tasks == null || tasks.isEmpty()){
            Log.e("AppPresenter","СерверХелпер отдал null или пустой лист тасков");
        }
        mDataBaseHelper.cacheTasks(tasks);
        mTaskDeskActivityWeakReference.get().showListTask(tasks);
    }



}
