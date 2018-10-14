package pro.sunriseforest.sunriseforestapp_client.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.presenter.LoginPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;

public class LoginActivity extends AppCompatActivity {
    private Button mLogInButton;
    private Button mRegistrationButton;
    private EditText mLoginEditText;
    private  EditText mPasswordEditText;


    private View.OnClickListener mOnClickListenerLogInButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        mPresenter.login(
                mLoginEditText.getText().toString(),
                mPasswordEditText.getText().toString()
        );
        }
    };

    private View.OnClickListener mOnClickListenerRegistrationButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.registration();
        }
    };


    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mLogInButton = findViewById(R.id.log_in_button);
        mLogInButton.setOnClickListener(mOnClickListenerLogInButton);

        mLoginEditText = findViewById(R.id.enter_login_editText);

        mPasswordEditText = findViewById(R.id.enter_password_editText);

        mRegistrationButton = findViewById(R.id.registration_logAct_button);
        mRegistrationButton.setOnClickListener(mOnClickListenerRegistrationButton);


        mPresenter =(LoginPresenter) PresenterManager.getInstance().getPresenter(LoginPresenter.TAG);
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

    public void showRegistrationActivity(){
        RegistrationActivity.startActivity(getApplicationContext());
    }

    public void showTaskDeskActivity(){
        TaskDeskActivity.startActivity(getApplicationContext());
    }

    public void showError(String msg){
        showToast(msg);
    }


    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}