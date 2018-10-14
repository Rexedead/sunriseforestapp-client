package pro.sunriseforest.sunriseforestapp_client.ui;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;
import pro.sunriseforest.sunriseforestapp_client.presenter.TaskDeskPresenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;


public class TaskDeskActivity extends AppCompatActivity{

    public static void startActivity(Context context){
        Intent intent = new Intent(context, TaskDeskActivity.class);
        context.startActivity(intent);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private TaskDeskPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdesk_activity);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mPresenter = (TaskDeskPresenter)PresenterManager.getInstance()
                .getPresenter(TaskDeskPresenter.TAG);

        mPresenter.bindActivity(this);
        mPresenter.initTaskDeskActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter.isActivityUnBunding()){
            mPresenter.bindActivity(this);
        }
        mPresenter.update();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unBindActivity();
    }

    public void showListTask(List<Task> tasks){
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Task.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        String jsonListTask = jsonAdapter.toJson(tasks);

        loadFragment(TaskDeskFragment.newInstance(jsonListTask));
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_taskdesk_activity, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
