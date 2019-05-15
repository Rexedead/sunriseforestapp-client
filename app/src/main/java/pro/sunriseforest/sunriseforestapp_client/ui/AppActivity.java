package pro.sunriseforest.sunriseforestapp_client.ui;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.notifications.JobBuilder;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;

public class AppActivity extends AppCompatActivity implements IView{


    private NavigationManager mNavigationManager;
    private NavController mNavController;
    private BottomNavigationView mBottomNavigationView;
    private boolean mIsStartApp;


    private BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            log(String.format("onNavigationItemSelected(menuItem = %s)", menuItem));
            switch (menuItem.getItemId()){
                case R.id.navigation_desk:
                    showDeskScreen();
                    break;
                case R.id.navigation_profile:
                    showProfileScreen();
                    break;
                case R.id.navigation_notifications:
                    showNotificationsScreen();
                    break;
            }
            menuItem.setChecked(true);
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("lc: onCreate()");

        mIsStartApp = savedInstanceState== null;

        setContentView(R.layout.app_activity);
        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mNavigationItemSelectedListener);

        mNavController = Navigation.findNavController(this, R.id.container);
        mNavigationManager = NavigationManager.getInstance();
        mNavigationManager.bindView(this);

        if(mIsStartApp){
            mNavigationManager.startApp();
        }
    }



    @Override
    public void showLoginScreen() {
        log("showLoginScreen()");


        switch (mNavController.getCurrentDestination().getId()){
            case R.id.splashFragment:
                mNavController.navigate(R.id.action_splashFragment_to_loginFragment);
                break;
            case R.id.profileFragment:
                mNavController.popBackStack(R.id.splashFragment, false);

                mNavController.navigate(R.id.action_splashFragment_to_loginFragment);
                break;
        }
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
                mNavController.popBackStack();
                mNavController.navigate(R.id.action_splashFragment_to_deskFragment);
                break;
            case R.id.registrationFragment:
                mNavController.popBackStack(R.id.splashFragment, false);
                mNavController.navigate(R.id.action_splashFragment_to_deskFragment);
                break;
            case R.id.profileFragment:
                mNavController.popBackStack();
                break;
            case R.id.notificationsFragment:
                mNavController.popBackStack();
                break;
        }

    }

    @Override
    public void showTask() {
        log(String.format("showTask()"));
        switch (mNavController.getCurrentDestination().getId()){
            case R.id.deskFragment:
                mNavController.navigate(R.id.action_deskFragment_to_taskFragment);
                break;
        }

    }

    @Override
    public void hideBottomNavigation() {
        log("hideBottomNavigation()");
        mBottomNavigationView.setVisibility(View.GONE);

    }

    @Override
    public void showBottomNavigation() {
        log("showBottomNavigation()");
        mBottomNavigationView.setVisibility(View.VISIBLE);
        log("s");
    }

    @Override
    public void showProfileScreen() {
        log("showProfileScreen()");
        switch (mNavController.getCurrentDestination().getId()){
            case R.id.deskFragment:
                mNavController.navigate(R.id.action_deskFragment_to_profileFragment);
                break;

            case R.id.notificationsFragment:
                mNavController.popBackStack();
                mNavController.navigate(R.id.action_deskFragment_to_profileFragment);
                break;
        }
    }



    @Override
    public void showNewTask() {
        log("showNewTask()");
        switch (mNavController.getCurrentDestination().getId()){
            case R.id.deskFragment:
                mNavController.navigate(R.id.action_deskFragment_to_newTaskFragment);
                break;
        }

    }

    @Override
    public void showNotificationsScreen() {
        log("showNotificationsScreen()");
        switch (mNavController.getCurrentDestination().getId()){
            case R.id.deskFragment:
                mNavController.navigate(R.id.action_deskFragment_to_notificationsFragment);
                break;
            case R.id.profileFragment:
                mNavController.popBackStack();
                mNavController.navigate(R.id.action_deskFragment_to_notificationsFragment);
                break;
        }
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



    public void setCheckedItemMenu(int idx){

        mBottomNavigationView.getMenu().getItem(idx).setChecked(true);
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
    public void onBackPressed() {
        int id = mNavController.getCurrentDestination().getId();

        if(id == R.id.deskFragment || id == R.id.loginFragment){
            //exit from app
            finish();
        }else{
            super.onBackPressed();
        }
    }


}
