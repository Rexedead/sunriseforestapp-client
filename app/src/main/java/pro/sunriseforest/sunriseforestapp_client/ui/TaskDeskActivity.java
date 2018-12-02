package pro.sunriseforest.sunriseforestapp_client.ui;

import pro.sunriseforest.sunriseforestapp_client.R;

import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;
import pro.sunriseforest.sunriseforestapp_client.presenter.TaskDeskPresenter;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.TaskDeskFragment;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.TaskFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;


public class TaskDeskActivity extends AppCompatActivity implements TaskDeskFragment.OnClickSingleRowListener {

    private TaskDeskPresenter mPresenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TaskDeskActivity.class);
        context.startActivity(intent);
    }


    View.OnClickListener mOnClickFabButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.fabAction();
        }
    };

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(TaskDeskActivity.this, ProfileActivity.class));
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdesk_activity);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(mOnClickFabButton);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mPresenter = (TaskDeskPresenter) PresenterManager.getInstance()
                .getPresenter(TaskDeskPresenter.TAG);

        mPresenter.bindActivity(this);
//        mPresenter.initTaskDeskActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter.isActivityUnBunding()) {
            mPresenter.bindActivity(this);

            setBottomMenuItem();
        }
        mPresenter.update();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unBindActivity();
    }

    @Override
    public void onBackPressed() {
        //Проверяем сколько фрагментов открыто в активити,
        // если > 0 - возвращяем предыдущий фрагмент
        // если < 0 - закрываем приложение
        Log.i(this.getClass().getName(),"getBackStackEntryCount(): " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }

    public void showListTask(List<Task> tasks) {
        String jsonListTask = jParser(tasks);
        initFragment(TaskDeskFragment.newInstance(jsonListTask), null);
        Log.i(this.getClass().getName(),"getBackStackEntryCount(): " + getSupportFragmentManager().getBackStackEntryCount());
    }

    public void showSingleTask(Task task) {
        String jsonTask = jParser(task);
        initFragment(TaskFragment.newInstance(jsonTask), "SingleTask");
        Log.i(this.getClass().getName(),"getBackStackEntryCount(): " + getSupportFragmentManager().getBackStackEntryCount());
    }

    private String jParser(List<Task> unparsedtask){
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Task.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.toJson(unparsedtask);
    }

    private String jParser(Task unparsedtask){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Task> jsonAdapter = moshi.adapter(Task.class);
        return jsonAdapter.toJson(unparsedtask);
    }

    public void initFragment(Fragment f, String backstack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.taskdesk_activity, f);
        transaction.addToBackStack(backstack);
        transaction.commit();
    }





    private void setBottomMenuItem() {
        BottomNavigationView mNavigation = findViewById(R.id.navigation);
        Menu menu = mNavigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

    public void createNewTask() {
        NewTaskActivity.startActivity(this);
    }


    @Override
    public void onClicked(View singleRowView, int position) {
        mPresenter.clickedSelectedTask(position);
    }
}


