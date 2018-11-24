package pro.sunriseforest.sunriseforestapp_client.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;
import pro.sunriseforest.sunriseforestapp_client.presenter.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity {

    Button mRemoveTokenButton;
    TextView mUserLoginTextView;

    private TextView mTextMessageTextView;
    private BottomNavigationView mNavigation;
    private ProfilePresenter mPresenter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    TaskDeskActivity.startActivity(ProfileActivity.this);
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:
                    mTextMessageTextView.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };



    private View.OnClickListener mOnClickRemoveTokenData = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.exitFromApp();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        mTextMessageTextView = findViewById(R.id.message);
        setBottomMenuItem();
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mPresenter =(ProfilePresenter) PresenterManager.getInstance().getPresenter(ProfilePresenter.TAG);

        mRemoveTokenButton = findViewById(R.id.removeToken_button);
        mRemoveTokenButton.setOnClickListener(mOnClickRemoveTokenData);

        mUserLoginTextView = findViewById(R.id.profileId_textView);
        mUserLoginTextView.setText(mPresenter.getUserLogin());

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter.isActivityUnBunding()){
            mPresenter.bindActivity(this);
        }
        mPresenter.update();
        setBottomMenuItem();

    }


    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unBindActivity();
    }

    private void setBottomMenuItem(){
        mNavigation = findViewById(R.id.navigation);
        Menu mMenu = mNavigation.getMenu();
        MenuItem menuItem = mMenu.getItem(1);
        menuItem.setChecked(true);
    }

    public void showLoginActivity() {
        LoginActivity.startActivity(this);
    }
}
