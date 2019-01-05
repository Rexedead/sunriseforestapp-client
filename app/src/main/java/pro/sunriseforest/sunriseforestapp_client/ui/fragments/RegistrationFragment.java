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

public class RegistrationFragment extends LogFragment {
    @Override
    protected String createTag() {
        return "RegistrationFragment";
    }

    private EditText mMailEditText;
    private EditText mPasswordEditText;
    private EditText mPhoneNumberEditText;
    private EditText mNameEditText;
    private AppPresenter mPresenter;

    private View.OnClickListener mOnClickListenerRegistrationButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            log("cl: RegistrationButton onClick()");
            String mail = mMailEditText.getText().toString();
            String phone = mPhoneNumberEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            mPresenter.selectedRegistration(new User(password, phone, mail, "contractor"));
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_fragment, container, false);

        Button registrationButton = view.findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(mOnClickListenerRegistrationButton);
        mMailEditText = view.findViewById(R.id.enter_mail_regAct_editText);
        mPasswordEditText = view.findViewById(R.id.enter_password_regAct_editText);
        mPhoneNumberEditText = view.findViewById(R.id.enter_phone_regAct_editText);
        mNameEditText = view.findViewById(R.id.enter_name_regAct_editText);

        mPresenter =  AppPresenter.getInstance();

        return view;
    }
}
