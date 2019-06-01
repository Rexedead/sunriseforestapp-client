package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.ProfilePresenter;


public class ProfileFragment extends NavigatedFragment  implements TextWatcher {
    private static final int ITEM_ON_NAV = 1;

    private TextView mUserIdTextView;
    private EditText mUserNameEditText;
    private EditText mUserMailEditText;
    private EditText mUserPhoneEditText;
    private TextView mUserRoleTextView;
    private TextView mUserTasksTakenStatsTextView;
    private TextView mUserRewardInfoTextView;
    private Button mSaveProfileButton;
    private Button mExitProfileButton;

    private ProfilePresenter mPresenter = ProfilePresenter.getInstance();


    private View.OnClickListener mExitProfileListener = view -> mPresenter.clickedExitProfile();
    private View.OnClickListener mSaveProfileListener = view -> mPresenter.clickedSaveButton();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        mUserIdTextView = view.findViewById(R.id.id_profileFrag_textView);
        mUserNameEditText = view.findViewById(R.id.user_name_profileFrag_editText);
        mUserMailEditText = view.findViewById(R.id.mail_profileFrag_editText);
        mUserPhoneEditText = view.findViewById(R.id.phone_profileFrag_editText);
        mUserRoleTextView = view.findViewById(R.id.role_profileFrag_textView);
        mUserTasksTakenStatsTextView = view.findViewById(R.id.tasks_taken_stats_profileFrag_textView);
        mUserRewardInfoTextView = view.findViewById(R.id.reward_profileFrag_textView);
        mExitProfileButton = view.findViewById(R.id.remove_token_profileFrag_button);
        mSaveProfileButton = view.findViewById(R.id.change_info_profileFrag_button);

        addListenersForEditText();
        showBottomNavigation();

        return view;
    }

    public void setProfile(String uId,String uName,
                            String uMail,String uPhone,
                            String uRole,int uTasksTaken,
                            int uReward) {
        log("setProfile(user = %s)");

        mUserIdTextView.setText(uId);
        mUserNameEditText.setText(uName);
        mUserMailEditText.setText(uMail);
        mUserPhoneEditText.setText(uPhone);
        mUserRoleTextView.setText(uRole);
        mUserTasksTakenStatsTextView.setText(String.valueOf(uTasksTaken));
        mUserRewardInfoTextView.setText(String.valueOf(uReward));
    }


    private void addListenersForEditText(){
        mExitProfileButton.setOnClickListener(mExitProfileListener);
        mSaveProfileButton.setOnClickListener(mSaveProfileListener);
        mUserNameEditText.addTextChangedListener(this);
        mUserMailEditText.addTextChangedListener(this);
        mUserPhoneEditText.addTextChangedListener(this);
    }


    public void setEnabledEditTexts(boolean isYes) {
        log("setEnabled( isYes = %s)", isYes);

        mUserNameEditText.setEnabled(false);
        mUserPhoneEditText.setEnabled(isYes);
        mUserMailEditText.setEnabled(isYes);
    }


    public void saveIsVisible(boolean showSaveButton){
        mSaveProfileButton.setVisibility(showSaveButton ? View.VISIBLE : View.GONE);
        }


    @Override
    protected String createTag() {
        return "ProfileFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public int getItemOnNavigationMenu() {
        return ITEM_ON_NAV;
    }

    public String getUserName() {
        return mUserNameEditText.getText().toString();
    }

    public String getUserMail() {
        return mUserMailEditText.getText().toString();
    }

    public String getUserPhone() {
        return mUserPhoneEditText.getText().toString();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String changedText = editable.toString();
        log("afterTextChanged: "+changedText);
        this.saveIsVisible(true);
    }
}