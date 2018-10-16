package pro.sunriseforest.sunriseforestapp_client.ui;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenter.AppPresenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;




public class TaskDeskActivity extends AppCompatActivity{


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

    private AppPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdesk_activity);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mPresenter = AppPresenter.getInstance();
        mPresenter.initApp(this);

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
        transaction.replace(R.id.taskdesk_activity, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}

interface ClickListener{
    void onClick(View view,int position);
    void onLongClick(View view,int position);
}


class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

        this.clicklistener=clicklistener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clicklistener!=null){
                    clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
            clicklistener.onClick(child,rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}