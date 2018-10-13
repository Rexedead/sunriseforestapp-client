package pro.sunriseforest.sunriseforestapp_client.presenter;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.date.DataBaseHelper;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.server.ServerHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.BulletinBoardActivity;

public class AppPresenter {

    private static AppPresenter sInstance;
    private DataBaseHelper mDataBaseHelper;
    private ServerHelper mServerHelper;

    private WeakReference<BulletinBoardActivity> mBulletinBoardActivityWeakReference;

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

    public void initApp(BulletinBoardActivity activity) {
        mBulletinBoardActivityWeakReference = new WeakReference<>(activity);

        List<Task> tasks = mServerHelper.getTasks();
        if(tasks == null || tasks.isEmpty()){
            Log.e("AppPresenter","СерверХелпер отдал null или пустой лист тасков");
        }
        mDataBaseHelper.cacheTasks(tasks);
        mBulletinBoardActivityWeakReference.get().showListTask(tasks);
    }



}
