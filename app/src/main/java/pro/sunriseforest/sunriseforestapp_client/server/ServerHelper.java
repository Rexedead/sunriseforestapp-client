package pro.sunriseforest.sunriseforestapp_client.server;


import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.Token;
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
    private List<Token> mTokens;

    private ServerHelper(){
        mTasks = Arrays.asList(
                new Task(1, "выруби лес плес)))0)","JAN 15", true),
                new Task(2, "го рубить лес"),
                new Task(3, "В России, столь могущественной своей матерьяльной силой" +
                        " и силой своего духа, нет войска; есть толпы угнетенных рабов, " +
                        "повинующихся ворам, угнетающим наемникам и грабителям, и в этой толпе нет " +
                        "ни преданности к царю, ни любви к отечеству — слова, которые так часто " +
                        "злоупотребляют,— ни рыцарской чести и отваги, есть с одной стороны дух терпения" +
                        " и подавленного ропота, с другой дух угнетения и лихоимства." , "JAN 333", true)
        );

        _mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
        mTokens = _mSharedPreferenceHelper._getTokensForServerHelper();

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

    public Token registration(String login, String password) throws IllegalArgumentException{
        _wait(2);
        if(_getToken(login, password) != null){
            throw new IllegalArgumentException("логин уже занят");
        }
        int id = _generateRandomTokenId();

        Token token = new Token(login, password, id);
        mTokens.add(token);
        _mSharedPreferenceHelper._saveTokenForServerHelper(token);
        return token;
    }
    public Token getToken(String login, String password){
        _wait(2);
        Token token = _getToken(login, password);
        if(token != null){
            return token;
        }
        throw new IllegalArgumentException("неверная пара или вы еще не зарегистрированы");
    }

    private Token _getToken(int id){
        for(Token token : mTokens){
            if(token.getTokenId() == id) return token;
        }
        return null;
    }
    private Token _getToken(String login, String password){
        for(Token token : mTokens){
            if(token.getLogin().equals(login) || token.getPassword().equals(password))
                return token;
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
