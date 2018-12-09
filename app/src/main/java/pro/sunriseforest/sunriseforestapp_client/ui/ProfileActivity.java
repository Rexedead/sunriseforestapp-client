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

import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;
import pro.sunriseforest.sunriseforestapp_client.presenter.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity {



    private TextView mTextMessageTextView;
    private BottomNavigationView mNavigation;
    private ProfilePresenter mProfilePresenter;
    private SharedPreferenceHelper mPreferenceHelper;

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
            mProfilePresenter.exitFromApp();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPreferenceHelper= new SharedPreferenceHelper(SunriseForestApp.getAppContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        mTextMessageTextView = findViewById(R.id.message);
        setBottomMenuItem();
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mProfilePresenter =(ProfilePresenter) PresenterManager.getInstance().getPresenter(ProfilePresenter.TAG);


        TextView mUserIdTextView = findViewById(R.id.profile_id_get_textView);
        mUserIdTextView.setText(Objects.requireNonNull(mPreferenceHelper.getMyUser()).getId());

        TextView mUserMailTextView = findViewById(R.id.profile_mail_get_textView);
        mUserMailTextView.setText(Objects.requireNonNull(mPreferenceHelper.getMyUser()).getEmail());

        TextView mUserPhoneTextView = findViewById(R.id.profile_phone_get_textView);
        mUserPhoneTextView.setText(Objects.requireNonNull(mPreferenceHelper.getMyUser()).getPhoneNumber());

        TextView mUserRoleTextView = findViewById(R.id.profile_role_get_textView);
        mUserRoleTextView.setText(Objects.requireNonNull(mPreferenceHelper.getMyUser()).getRole());

        Button mRemoveTokenButton = findViewById(R.id.removeToken_button);
        mRemoveTokenButton.setOnClickListener(mOnClickRemoveTokenData);


    }



    @Override
    protected void onResume() {
        super.onResume();
        if(mProfilePresenter.isActivityUnBunding()){
            mProfilePresenter.bindActivity(this);
        }
        mProfilePresenter.update();
        setBottomMenuItem();

    }


    @Override
    protected void onPause() {
        super.onPause();
        mProfilePresenter.unBindActivity();
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
