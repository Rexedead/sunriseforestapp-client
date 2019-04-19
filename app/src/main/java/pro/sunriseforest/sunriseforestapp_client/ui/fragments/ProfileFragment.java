package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;


public class ProfileFragment extends BaseFragment {

    SharedPreferenceHelper mPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    protected String createTag() {
        return "ProfileFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return new BasePresenter() {
            @Override
            protected String getTAG() {
                return "хузентер";
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO ... переделать все по принципу TaskFragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        final TextView mUserIdTextView = view.findViewById(R.id.profile_id_get_textView);
        mUserIdTextView.setText(Objects.requireNonNull(mPreferenceHelper.getUser()).getId());

        final EditText mUserNameEditText = view.findViewById(R.id.profile_name_get_editText);
        mUserNameEditText.setText(mPreferenceHelper.getUser().getName());

        final EditText mUserMailEditText = view.findViewById(R.id.profile_mail_get_editText);
        mUserMailEditText.setText(mPreferenceHelper.getUser().getEmail());

        final EditText mUserPhoneEditText = view.findViewById(R.id.profile_phone_get_editText);
        mUserPhoneEditText.setText(mPreferenceHelper.getUser().getPhoneNumber());

        TextView mUserRoleTextView = view.findViewById(R.id.profile_role_get_textView);
        mUserRoleTextView.setText(mPreferenceHelper.getUser().getRole());


        TextView mUserTasksTakenStatsTextView = view.findViewById(R.id.profile_tasksTaken_get_textView);
//        mUserTasksTakenStatsTextView.setText(mPreferenceHelper.getUser().getTasksCount());

        TextView mUserRewardInfoTextView = view.findViewById(R.id.profile_reward_get_textView);
//        mUserRewardInfoTextView.setText(mPreferenceHelper.getUser().getRewardSum());

        Button mRemoveTokenButton = view.findViewById(R.id.removeToken_button);
//        mRemoveTokenButton.setOnClickListener(mOnClickRemoveTokenData);

        final Button mEditProfileButton = view.findViewById(R.id.changeInfo_button);
        mEditProfileButton.setText("Редактировать");

        mUserNameEditText.setEnabled(false);
        mUserPhoneEditText.setEnabled(false);
        mUserMailEditText.setEnabled(false);


        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            private boolean edit = false;

            @Override
            public void onClick(View v) {
                if (edit) {
                    mEditProfileButton.setText("Редактировать");
                    mUserNameEditText.setEnabled(false);
                    mUserPhoneEditText.setEnabled(false);
                    mUserMailEditText.setEnabled(false);
                    edit = false;
                    User user = mPreferenceHelper.getUser();
                    user.setName(mUserNameEditText.getText().toString());
                    user.setEmail(mUserMailEditText.getText().toString());
                    user.setPhoneNumber(mUserPhoneEditText.getText().toString());
//                    AppPresenter.getInstance().editProfile(user);
                } else {
                    mEditProfileButton.setText("Сохранить");
                    mUserNameEditText.setEnabled(true);
                    mUserPhoneEditText.setEnabled(true);
                    mUserMailEditText.setEnabled(true);
                    edit = true;
                }


            }
        });


        showBottomNavigation();

        return view;
    }

//    private View.OnClickListener mOnClickRemoveTokenData = view -> AppPresenter.getInstance().exitProfile();

}

