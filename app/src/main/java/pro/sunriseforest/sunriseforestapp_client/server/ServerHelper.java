package pro.sunriseforest.sunriseforestapp_client.server;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;

public class ServerHelper {

    private static ServerHelper sInstance;
    private static SharedPreferenceHelper _mSharedPreferenceHelper;

    public static ServerHelper getInstance(){
        if(sInstance == null){
            sInstance = new ServerHelper();
        }

        return sInstance;
    }

    private List<Task> mTasks;
    private List<User> mUsers;

    private ServerHelper(){

        mTasks = Arrays.asList(
                new Task(1,
                        "выруби лес плес)))0)",
                        "23:00 14.06.2018",
                        "Вепсский лес",
                        "улица пушкина дом калатушкина",
                        500
                        ),


                new Task(2,
                        "го рубить лес",
                        "23:00 16.06.2018",
                        "Саблинский заповедник",
                        "ул. Сибирская 2",
                        400
                        ),
                new Task(3,
                        "В России, столь могущественной своей матерьяльной силой" +
                        " и силой своего духа, нет войска; есть толпы угнетенных рабов, " +
                        "повинующихся ворам, угнетающим наемникам и грабителям, и в этой толпе нет " +
                        "ни преданности к царю, ни любви к отечеству — слова, которые так часто " +
                        "злоупотребляют,— ни рыцарской чести и отваги, есть с одной стороны дух терпения" +
                        " и подавленного ропота, с другой дух угнетения и лихоимства." ,
                        "23:00 16.06.2018",
                        "Линдуловский заказник",
                        "ул. Ленина, дом 56",
                        100500)
        );

        _mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
        mUsers = _mSharedPreferenceHelper._getTokensForServerHelper();

    }

    public List<Task> getTasks() {
        _wait(3);
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

    public User registration(String login, String password) throws IllegalArgumentException{
        _wait(0);
        if(_getToken(login, password) != null){
            throw new IllegalArgumentException("логин уже занят");
        }
        int id = _generateRandomTokenId();

        User user = new User(login, password, id);
        mUsers.add(user);
        _mSharedPreferenceHelper._saveTokenForServerHelper(user);
        return user;
    }
    public User getToken(String login, String password){
        _wait(0);
        User user = _getToken(login, password);
        if(user != null){
            return user;
        }
        throw new IllegalArgumentException("неверная пара или вы еще не зарегистрированы");
    }

    private User _getToken(int id){
        for(User user : mUsers){
            if(user.getTokenId() == id) return user;
        }
        return null;
    }
    private User _getToken(String login, String password){
        for(User user : mUsers){
            if(user.getLogin().equals(login) || user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    private void _wait(int seconds){
        try {
            int a = (int) (Math.random()*seconds);
            TimeUnit.SECONDS.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int _generateRandomTokenId(){
        return (int)(Math.random()*Integer.MAX_VALUE);
    }

}
