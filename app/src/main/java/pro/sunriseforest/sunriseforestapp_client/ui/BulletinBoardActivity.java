package pro.sunriseforest.sunriseforestapp_client.ui;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Ad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;





public class BulletinBoardActivity extends AppCompatActivity implements TaskFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private List<Ad> mTaskList = new ArrayList<>();
    private RecycleTaskAdapter mRecycleTaskAdapter;
    public Context context;


    public Context getContext() {
        return context;
    }


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    prepareTaskData();
                    InitRecycleView();
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        InitRecycleView();

    }

    private void InitRecycleView() {

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecycleTaskAdapter = new RecycleTaskAdapter(mTaskList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecycleTaskAdapter);


    }

    private void prepareTaskData() {
        Ad miniTask = new Ad(1, "20.01", true);
        mTaskList.add(miniTask);

        miniTask = new Ad(2, "20.01", true);
        mTaskList.add(miniTask);

        miniTask = new Ad(3, "20.01", true);
        mTaskList.add(miniTask);


        mRecycleTaskAdapter.notifyDataSetChanged();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
