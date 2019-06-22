package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.RegistrationPresenter;


public class RegistrationFragment extends BaseFragment {

    private RegistrationPresenter mRegistrationPresenter = RegistrationPresenter.getInstance();


    private EditText mMailEditText;
    private EditText mPasswordEditText;
    private EditText mPhoneNumberEditText;
    private EditText mNameEditText;


    private View.OnClickListener mOnClickListenerRegistrationButton =
            view -> mRegistrationPresenter.selectedRegistration(getUser());

    private User getUser(){
        String mail = mMailEditText.getText().toString().trim();
        String phone = mPhoneNumberEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String name = mNameEditText.getText().toString().trim();
        return new User(name, password, phone, mail, "contractor");
    }

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

        final MaskedTextChangedListener maskedPhoneTextChangedListener = MaskedTextChangedListener
                .Companion
                .installOn(
                        mPhoneNumberEditText,
                        "+7 ([000]) [000]-[00]-[00]",
                        (maskFilled, extractedValue) -> {}
                );

        mPhoneNumberEditText.setHint(maskedPhoneTextChangedListener.placeholder());
        hideBottomNavigation();
        return view;
    }

    @Override
    protected String createTag() {
        return "RegistrationFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return mRegistrationPresenter;
    }


}
