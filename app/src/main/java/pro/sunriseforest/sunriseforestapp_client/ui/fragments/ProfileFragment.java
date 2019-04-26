package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.ProfilePresenter;


public class ProfileFragment extends NavigatedFragment{
    private static final int ITEM_ON_NAV = 1;


    private User mProfileData;
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

        mSaveProfileButton.setText("Сохранить");

        mExitProfileButton.setOnClickListener(mExitProfileListener);
        mSaveProfileButton.setOnClickListener(v -> mPresenter.clickedSaveButton()); // TODO вынеси лисенер в поле(пример строкой выше)
        mUserNameEditText.addTextChangedListener(mPresenter);
        mUserMailEditText.addTextChangedListener(mPresenter);
        mUserPhoneEditText.addTextChangedListener(mPresenter);

        showBottomNavigation();

        return view;
    }

    public void showProfile(User user) {
        log("showProfileScreen(user = %s)", user);
        setProfile(user);
    }

    private void setProfile(User user) {
        log("setProfile(user = %s)", user);
        mProfileData = user;
        mUserIdTextView.setText(mProfileData.getId());
        mUserNameEditText.setText(mProfileData.getName());
        mUserMailEditText.setText(mProfileData.getEmail());
        mUserPhoneEditText.setText(mProfileData.getPhoneNumber());
        mUserRoleTextView.setText(mProfileData.getRole());
//                mUserTasksTakenStatsTextView.setText(mPreferenceHelper.getUser().getTasksCount());
        //        mUserRewardInfoTextView.setText(mPreferenceHelper.getUser().getRewardSum());
        //        mExitProfileButton.setOnClickListener(mOnClickRemoveTokenData);
    }


    public void setEnabledEditTexts(boolean isYes) {
        log("setEnabled( isYes = %s)", isYes);

        mUserNameEditText.setEnabled(isYes);
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
    public void onCreate(Bundle savedInstanceState) {//TODO что делает этот метод?
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getItemOnNavigationMenu() {
        return ITEM_ON_NAV;
    }
}



//    private View.OnClickListener mOnClickRemoveTokenData = view -> AppPresenter.getInstance().exitProfile();


