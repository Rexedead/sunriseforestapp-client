package pro.sunriseforest.sunriseforestapp_client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;
import pro.sunriseforest.sunriseforestapp_client.presenter.RegistrationPresenter;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mMailEditText;
    private EditText mPasswordEditText;
    private EditText mPhoneNumberEditText;
    private EditText mNameEditText;

    private RegistrationPresenter mRegistrationPresenter;

    public static void startActivity(Context context){
        Intent intent = new Intent(context, RegistrationActivity.class);
        context.startActivity(intent);

    }

    private View.OnClickListener mOnClickListenerLogInButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String mail = mMailEditText.getText().toString();
            String phone = mPhoneNumberEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();


            mRegistrationPresenter.registration(new User(password, phone, mail));
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        Button mRegistrationButton = findViewById(R.id.registration_button);
        mRegistrationButton.setOnClickListener(mOnClickListenerLogInButton);
        mMailEditText = findViewById(R.id.enter_mail_regAct_editText);
        mPasswordEditText = findViewById(R.id.enter_password_regAct_editText);
        mPhoneNumberEditText = findViewById(R.id.enter_phone_regAct_editText);
        mNameEditText = findViewById(R.id.enter_name_regAct_editText);

        mRegistrationPresenter = (RegistrationPresenter) PresenterManager.getInstance()
                .getPresenter(RegistrationPresenter.TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mRegistrationPresenter.isActivityUnBunding()){
            mRegistrationPresenter.bindActivity(this);
        }
        mRegistrationPresenter.update();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRegistrationPresenter.unBindActivity();
    }

    public void showTaskDeskActivity(){
        TaskDeskActivity.startActivity(this.getApplicationContext());
        showToast("регистрация прошла успешно");
    }

    public void showError(String msg){
        showToast(msg);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
