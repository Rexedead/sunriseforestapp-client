package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
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


public class ProfileFragment extends NavigatedFragment  implements TextWatcher, SwipeRefreshLayout.OnRefreshListener {
    private static final int ITEM_ON_NAV = 1;


    private TextView mUserNameTextView;
    private EditText mUserMailEditText;
    private EditText mUserPhoneEditText;
    private TextView mUserTasksTakenStatsTextView;
    private TextView mUserRewardInfoTextView;
    private Button mSaveButton;
    private Button mCancelChangesButton;
    private Button mExitProfileButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProfilePresenter mPresenter = ProfilePresenter.getInstance();

    private View.OnClickListener mExitProfileListener = view -> mPresenter.clickedExitProfile();
    private View.OnClickListener mSaveProfileListener = view -> mPresenter.clickedSaveButton();
    private View.OnClickListener mCancelChangesListener = view -> mPresenter.clickedCancelChangesButton();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        addListenersForEditText();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);


        mUserNameTextView = view.findViewById(R.id.userName_profileFrag_textView);
        mUserMailEditText = view.findViewById(R.id.mail_profileFrag_editText);
        mUserPhoneEditText = view.findViewById(R.id.phone_profileFrag_editText);
        mUserTasksTakenStatsTextView = view.findViewById(R.id.tasks_taken_stats_profileFrag_textView);
        mUserRewardInfoTextView = view.findViewById(R.id.reward_profileFrag_textView);
        mExitProfileButton = view.findViewById(R.id.remove_token_profileFrag_button);
        mSaveButton = view.findViewById(R.id.change_info_profileFrag_button);
        mCancelChangesButton = view.findViewById(R.id.cancel_changes_profileFrag_button);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_profileFrag);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        mExitProfileButton.setOnClickListener(mExitProfileListener);
        mSaveButton.setOnClickListener(mSaveProfileListener);
        mCancelChangesButton.setOnClickListener(mCancelChangesListener);

        showBottomNavigation();

        return view;
    }

    public void showProfile(User u) {
        log("showProfile(user = %s)");

        mUserNameTextView.setText(u.getName());
        mUserMailEditText.setText(u.getEmail());
        mUserMailEditText.setSelection(mUserMailEditText.getText().toString().length());
        mUserPhoneEditText.setText(u.getPhoneNumber());
        mUserPhoneEditText.setSelection(mUserPhoneEditText.getText().toString().length());
        mUserTasksTakenStatsTextView.setText(String.valueOf(u.getTasksCount()));
        mUserRewardInfoTextView.setText(String.valueOf(u.getRewardSum()));
    }


    private void addListenersForEditText(){
        mUserMailEditText.addTextChangedListener(this);
        mUserPhoneEditText.addTextChangedListener(this);
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
        mPresenter.descriptionProfileIsChanged();
    }

    //swipe refresh listener
    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    public void showLoading(){
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void hideLoading(){
        mSwipeRefreshLayout.setRefreshing(false);
    }


    public void showSaveViews() {
        log("showSaveViews()");
        mCancelChangesButton.setVisibility(View.VISIBLE);
        mSaveButton.setVisibility(View.VISIBLE);
    }

    public void hideSaveViews(){
        log("hideSaveViews()");
        mCancelChangesButton.setVisibility(View.GONE);
        mSaveButton.setVisibility(View.GONE);
    }
}