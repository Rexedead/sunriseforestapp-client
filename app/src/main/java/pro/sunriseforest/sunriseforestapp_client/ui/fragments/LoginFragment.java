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
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.LoginPresenter;

public class LoginFragment extends BaseFragment  {
    private EditText mLoginEditText;
    private EditText mPasswordEditText;
    private LoginPresenter mLoginPresenter = LoginPresenter.getInstance();

    private View.OnClickListener mOnClickListenerLogInButton = view -> {
        log("cl: LoginButton onClick()");
        User user = getUser();
        mLoginPresenter.selectedLogin(user);
    };

    private View.OnClickListener mOnClickListenerRegistrationButton = view -> {
        log("cl: RegistrationButton onClick()");
        mLoginPresenter.selectedGoToRegistration();

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        Button logInButton = view.findViewById(R.id.log_in_button);
        logInButton.setOnClickListener(mOnClickListenerLogInButton);

        mLoginEditText = view.findViewById(R.id.enter_login_editText);

        mPasswordEditText = view.findViewById(R.id.enter_password_editText);

        Button registrationButton = view.findViewById(R.id.registration_logAct_button);
        registrationButton.setOnClickListener(mOnClickListenerRegistrationButton);

        hideBottomNavigation();

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

    @Override
    protected BasePresenter getPresenter() {
        return mLoginPresenter;
    }
}
