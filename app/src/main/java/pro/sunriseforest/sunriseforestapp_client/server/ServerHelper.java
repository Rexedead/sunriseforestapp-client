package pro.sunriseforest.sunriseforestapp_client.server;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pro.sunriseforest.sunriseforestapp_client.models.Task;

public class ServerHelper {

    private static ServerHelper sInstance;

    public static ServerHelper getInstance(){
        if(sInstance == null){
            sInstance = new ServerHelper();
        }

        return sInstance;
    }

    private List<Task> mTasks;

    private ServerHelper(){
        mTasks = Arrays.asList(
                new Task(1, "выруби лес плес)))0)"),
                new Task(2, "го рубить лес"),
                new Task(3, "В России, столь могущественной своей матерьяльной силой" +
                        " и силой своего духа, нет войска; есть толпы угнетенных рабов, " +
                        "повинующихся ворам, угнетающим наемникам и грабителям, и в этой толпе нет " +
                        "ни преданности к царю, ни любви к отечеству — слова, которые так часто " +
                        "злоупотребляют,— ни рыцарской чести и отваги, есть с одной стороны дух терпения" +
                        " и подавленного ропота, с другой дух угнетения и лихоимства.")
        );

    }

    public List<Task> getTasks() {
//        _wait(4);
        List<Task> copyTasks = new ArrayList<>();
        for(Task task : mTasks){
            copyTasks.add(task.copy());
        }
        return copyTasks;
    }

    private @Nullable
    Task _getTaskById(int id){

        for(Task _task : mTasks){
            if(_task.getId() == id) return _task;
        }

        return null;
    }

    public @Nullable
    Task getTaskById(int id){
        Task task = _getTaskById(id);
        return task == null ? null : task.copy();
    }

    public boolean sendTask(Task task){


        for(int i = 0; i < mTasks.size(); i++){
            if(mTasks.get(i).getId() == task.getId()){
                mTasks.set(i, task);
                return true;
            }
        }
        return false;
    }

    private void _wait(int seconds){
        try {
            int a = (int) (Math.random()*seconds);
            TimeUnit.SECONDS.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
