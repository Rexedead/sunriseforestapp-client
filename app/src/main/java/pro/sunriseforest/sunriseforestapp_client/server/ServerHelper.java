package pro.sunriseforest.sunriseforestapp_client.server;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.WaitTemp;

public class ServerHelper {

    private static ServerHelper sInstance;

    public static ServerHelper getInstance(){
        if(sInstance == null){
            sInstance = new ServerHelper();
        }

        return sInstance;
    }

    private List<Task> mTasks;

    public ServerHelper(){
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
        List<Task> copyTasks = new ArrayList<>();
        for(Task ts : mTasks){
            copyTasks.add(ts.copy());
        }
        WaitTemp.delay();
        return copyTasks;
    }

    private @Nullable
    Task _getTaskById(int id){

        for(Task _ts : mTasks){
            if(_ts.getId() == id) return _ts;
        }

        return null;
    }

    public @Nullable Task getTaskById(int id){
        Task ts = _getTaskById(id);
        return ts == null ? null : ts.copy();
    }

    public boolean sendTask(Task ts){


        for(int i = 0; i < mTasks.size(); i++){
            if(mTasks.get(i).getId() == ts.getId()){
                mTasks.set(i, ts);
                return true;
            }
        }
        return false;
    }


}
