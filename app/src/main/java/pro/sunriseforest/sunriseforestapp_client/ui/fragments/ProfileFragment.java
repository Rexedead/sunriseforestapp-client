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


public class ProfileFragment extends BaseFragment {
    private User mProfileData;
    private TextView mUserIdTextView;
    private EditText mUserNameEditText;
    private EditText mUserMailEditText;
    private EditText mUserPhoneEditText;
    private TextView mUserRoleTextView;
    private TextView mUserTasksTakenStatsTextView;
    private TextView mUserRewardInfoTextView;
    private Button mEditProfileButton;
    private Button mRemoveTokenButton;

    private ProfilePresenter mPresenter = ProfilePresenter.getInstance();


    public ProfileFragment() {
        // Required empty public constructor
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mRemoveTokenButton = view.findViewById(R.id.remove_token_profileFrag_button);
        mEditProfileButton = view.findViewById(R.id.change_info_profileFrag_button);

        mEditProfileButton.setText("Сохранить");

        mEditProfileButton.setOnClickListener(v -> mPresenter.clickedSaveButton());
        mUserNameEditText.addTextChangedListener(mPresenter);
        mUserMailEditText.addTextChangedListener(mPresenter);
        mUserPhoneEditText.addTextChangedListener(mPresenter);

        showBottomNavigation();

        return view;
    }

    public void showProfile(User user) {
        log("showProfile(user = %s)", user);
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
        //        mRemoveTokenButton.setOnClickListener(mOnClickRemoveTokenData);
    }


    public void setEnabledEditTexts(boolean isYes) {
        log("setEnabled( isYes = %s)", isYes);

        mUserNameEditText.setEnabled(isYes);
        mUserPhoneEditText.setEnabled(isYes);
        mUserMailEditText.setEnabled(isYes);
    }


    public void saveIsVisible(boolean showSaveButton){
        mEditProfileButton.setVisibility(showSaveButton ? View.VISIBLE : View.GONE);
        }

    }

//    private View.OnClickListener mOnClickRemoveTokenData = view -> AppPresenter.getInstance().exitProfile();


