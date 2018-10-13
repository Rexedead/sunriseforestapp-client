package pro.sunriseforest.sunriseforestapp_client.date;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import pro.sunriseforest.sunriseforestapp_client.models.Task;

public class DataBaseHelper {


    private static  DataBaseHelper sInstance;

    public static DataBaseHelper getInstance(){
        if(sInstance == null){
            sInstance = new DataBaseHelper();
        }
        return sInstance;
    }

    private List<Task> mTasks;
    private DataBaseHelper(){
        mTasks = new ArrayList<>();
    }

    public List<Task> getTasks(){

        List<Task> copyTasks = new ArrayList<>();
        for(Task ts : mTasks){
            copyTasks.add(ts.copy());
        }

        return copyTasks;
    }

    private @Nullable Task _getTaskById(int id){

        for(Task _task : mTasks){
            if(_task.getId() == id) return _task;
        }

        return null;
    }

    public @Nullable Task getTaskById(int id){
        Task ad = _getTaskById(id);
        return ad == null ? null : ad.copy();
    }

    public void cacheTask(Task ts){
       for(int i = 0; i < mTasks.size(); i++){
           if(mTasks.get(i).getId() == ts.getId()){
               mTasks.set(i, ts);
               return;
           }
       }
       mTasks.add(ts);
    }

    public void cacheTasks(List<Task> tsk){
        for(Task ts: tsk){
            cacheTask(ts);
        }
    }
    
}
