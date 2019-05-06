package pro.sunriseforest.sunriseforestapp_client.ui;

import android.util.Log;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;

public class NavigationManager {
    private static final NavigationManager ourInstance = new NavigationManager();


    public static NavigationManager getInstance() {
        return ourInstance;
    }

    private boolean mIsBunding;

    private String TAG = "%%%/nav_man";

    private AppActivity mAppActivity;

    private NavigationManager() {

    }

    public void startApp(){
        log("startApp()");
        User user = new  SharedPreferenceHelper(SunriseForestApp.getAppContext()).getUser();

        try{
            if (user == null) {
                mAppActivity.showLoginScreen();
            } else {
                mAppActivity.showDeskScreen();
            }
        }catch (NullPointerException e){
            log("startApp(): NULLPOINTEREXCEPTION стартуешь приложение, а в НавигейшенМанагере активити не забайндили, вообще-то" );
        }

    }

    public void fromLoginToDesk(){
        log("fromLoginToDesk()");

        if(!activityIsNullAndLog("fromLoginToDesk()")){
            mAppActivity.showDeskScreen();
        }

        //...
    }

    public void fromProfileToLogin(){
        log("fromProfileToLogin() ");

        if(!activityIsNullAndLog("fromProfileToLogin()")){
            mAppActivity.showLoginScreen();
        }
    }

    public void fromLoginToRegistration(){
        log("fromLoginToRegistration");

        if(!activityIsNullAndLog("fromLoginToRegistration")){
            mAppActivity.showRegistrationScreen();
        }


    }


    public void fromDeskToNewTask(){
        log("fromDeskToNewTask()");

        if(!activityIsNullAndLog("fromDeskToNewTask()")){
            mAppActivity.showNewTask();
        }
    }
    public void fromRegistrationToDesk(){
        log("fromRegistrationToDesk()");
        if(!activityIsNullAndLog("fromRegistrationToDesk()")){
            mAppActivity.showDeskScreen();
        }

    }

    public void fromDeskToTask(){
        log("fromDeskToTask()");
        mAppActivity.showTask();
    }
    public void back(){
        log("back");
        mAppActivity.onBackPressed();
    }

    public void bindView(AppActivity view){
        log(String.format("bindView(view=%s)", view));
        mAppActivity = view;
        mIsBunding = true;

    }

    public void unBindView(){
        log("unBindView()");
        mAppActivity = null;
        mIsBunding = false;
    }

    private boolean activityIsNullAndLog(String nameMethod){
        if (mAppActivity == null) {
            log(nameMethod + ": activity is null");
            return true;
        }else{
            return false;
        }

    }

    public boolean isBinding(){
        return mIsBunding;
    }

    protected void log(String msg){
        Log.i(TAG,msg);
    }

}
