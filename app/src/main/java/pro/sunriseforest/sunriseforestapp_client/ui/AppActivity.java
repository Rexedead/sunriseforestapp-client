package pro.sunriseforest.sunriseforestapp_client.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;

public class AppActivity extends AppCompatActivity implements IView{


    private NavigationManager mNavigationManager;
    private NavController mNavController;
    private boolean mIsStartApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("lc: onCreate()");

        mIsStartApp = savedInstanceState== null;

        setContentView(R.layout.app_activity);

        mNavController = Navigation.findNavController(this, R.id.container);
        mNavigationManager = NavigationManager.getInstance();
        mNavigationManager.bindView(this);

        if(mIsStartApp){
            mNavigationManager.startApp();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        log("lc: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("lc: onRestart()");
    }



    @Override
    public void showLoginScreen() {
        log("showLoginScreen()");
        mNavController.navigate(R.id.action_splashFragment_to_loginFragment);
    }

    @Override
    public void showRegistrationScreen() {
        log("showRegistrationScreen()");
        mNavController.navigate(R.id.action_loginFragment_to_registrationFragment);

    }

    @Override
    public void showDeskScreen() {
        log("showDeskScreen()");

        switch (mNavController.getCurrentDestination().getId()){
            case R.id.splashFragment:
                mNavController.navigate(R.id.action_splashFragment_to_deskFragment);
                break;
            case R.id.loginFragment:
                mNavController.navigate(R.id.action_loginFragment_to_deskFragment);
                break;
        }

    }

    @Override
    public void showTasks(List<Task> tasks) {
        log(String.format("showTasks(tasks) length of tasks = %s", tasks.size()));
    }

    @Override
    public void showTask(int position) {
        log(String.format("showTask(position=%s)", position));

    }

    @Override
    public void showProfile() {
        log("showProfile()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        log("lc: onResume()");

        if(!mNavigationManager.isBinding()){
            mNavigationManager.bindView(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("lc: onPause()");

        mNavigationManager.unBindView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("lc: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("lc: onDestroy()");
    }

    @Override
    public void showNewTask() {
        log("showNewTask()");

    }

    @Override
    public void showError(String msg) {
        log("showError() msg="+msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showLoading() {
        log("showLoading()");

    }

    @Override
    public void showInfoMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void log(String msg){
        Log.i("%%%/activity", msg);
    }
}
