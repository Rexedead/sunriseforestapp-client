package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.presenter.AppPresenter;

public class LoginFragment extends LogFragment {
    private EditText mLoginEditText;
    private  EditText mPasswordEditText;

    private View.OnClickListener mOnClickListenerLogInButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            log("cl: LoginButton onClick()");
            User user = getUser();
            AppPresenter.getInstance().selectedLogin(user);
        }
    };

    private View.OnClickListener mOnClickListenerRegistrationButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            log("cl: RegistrationButton onClick()");
            AppPresenter.getInstance().selectedGotoRegistration();

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        Button mLogInButton = view.findViewById(R.id.log_in_button);
        mLogInButton.setOnClickListener(mOnClickListenerLogInButton);

        mLoginEditText = view.findViewById(R.id.enter_login_editText);

        mPasswordEditText = view.findViewById(R.id.enter_password_editText);

        Button mRegistrationButton = view.findViewById(R.id.registration_logAct_button);
        mRegistrationButton.setOnClickListener(mOnClickListenerRegistrationButton);

        return view;
    }

    private User getUser(){

        String login = mLoginEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        return new User(login, password);
    }

    @Override
    protected String createTag() {
        return "LoginFragment";
    }
}
