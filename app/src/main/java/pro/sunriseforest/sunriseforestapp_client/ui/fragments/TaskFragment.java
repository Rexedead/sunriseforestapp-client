package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.TaskPresenter;


public class TaskFragment extends BaseFragment
        implements TextWatcher, SwipeRefreshLayout.OnRefreshListener {

    private TaskPresenter mPresenter = TaskPresenter.getInstance();

    private TextView mIdTextView;
    private EditText mDescriptionEditText;
    private TextInputEditText mTaskStartDateTextInputEditText;
    private TextInputEditText mTaskEndDateTextInputEditText;
    private EditText mRewardEditText;
    private EditText mClientPhoneEditText;
    private EditText mClientNameEditText;
    private Button mSaveButton;
    private Button mCancelChangesButton;

    private TextView mContractorTextView;
    private TextView mContractorNameTextView;
    private TextView mContractorPhoneTextView;
    private TextView mClientTextView;
    private Button mBookButton;
    private Button mCompleteTaskButton;
    private Button mCancelTaskButton;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private View.OnClickListener mSaveTaskListener = view -> mPresenter.clickedSaveButton();
    private View.OnClickListener mCancelChangesListener = view -> mPresenter.clickedCancelChangesButton();

    private View.OnClickListener mBookListener = view -> mPresenter.clickedBookButton();
    private View.OnClickListener mCompleteListener = view -> mPresenter.clickedCompleteButton();
    private View.OnClickListener mCancelListener = view -> mPresenter.clickedCancelTaskButton();

    private DatePickerDialog.OnDateSetListener mOnStartDateChangeListener =
            (view, year, month, dayOfMonth) -> mPresenter.changedStartDate(year, month, dayOfMonth);
    private DatePickerDialog.OnDateSetListener  mOnDateEndChangeListener =
            (view, year, month, dayOfMonth) ->mPresenter.changedEndDate(year, month, dayOfMonth) ;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.task_fragment, container, false);
        mIdTextView = view.findViewById(R.id.id_taskFrag_textView);
        mDescriptionEditText = view.findViewById(R.id.description_taskFrag_editText);
        mTaskStartDateTextInputEditText = view.findViewById(R.id.start_date_taskFrag_TextInputEditText);
        mTaskEndDateTextInputEditText = view.findViewById(R.id.end_date_taskFrag_TextInputEditText);
        mRewardEditText = view.findViewById(R.id.reward_taskFrag_editText);
        mClientTextView = view.findViewById(R.id.client_taskFrag_TextView);
        mClientPhoneEditText = view.findViewById(R.id.client_phone_taskFrag_editText);
        mClientNameEditText = view.findViewById(R.id.client_name_taskFrag_editText);
        mSaveButton = view.findViewById(R.id.save_taskFrag_button);
        mCancelChangesButton = view.findViewById(R.id.cancel_changes_taskFrag_button);
        mContractorTextView = view.findViewById(R.id.contractor_taskFrag_TextView);
        mContractorNameTextView = view.findViewById(R.id.contractor_name_taskFrag_textView);
        mContractorPhoneTextView = view.findViewById(R.id.contractor_phone_taskFrag_textView);
        mBookButton = view.findViewById(R.id.book_taskFrag_button);
        mCompleteTaskButton = view.findViewById(R.id.complete_taskFrag_button);
        mCancelTaskButton = view.findViewById(R.id.cancel_taskFrag_button);

        mRewardEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                setCursorPositionForRewardEditText();
            }
        });

        mRewardEditText.setOnClickListener(v-> setCursorPositionForRewardEditText());


        mTaskEndDateTextInputEditText.setOnClickListener(v -> mPresenter.clickedEndDate());
        mTaskStartDateTextInputEditText.setOnClickListener(v -> mPresenter.clickedStartDate());

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_taskFrag);
        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setProgressViewOffset(false, 0,1000);

        mCancelChangesButton.setOnClickListener(mCancelChangesListener);
        mSaveButton.setOnClickListener(mSaveTaskListener);
        mBookButton.setOnClickListener(mBookListener);
        mCompleteTaskButton.setOnClickListener(mCompleteListener);
        mCancelTaskButton.setOnClickListener(mCancelListener);


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        addListenersForEditText();
    }

    public void showLoading(){
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void hideLoading(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void showStartDatePickerDialog(int year, int month, int day ){
        log("showStartDatePickerDialog()");
        createDatePickerDialog(year, month, day, mOnStartDateChangeListener).show();
    }

    public void showEndDatePickerDialog(int year, int month, int day){
        log("showEndDatePickerDialog()");
        createDatePickerDialog(year, month, day, mOnDateEndChangeListener).show();
    }

    private DatePickerDialog createDatePickerDialog(int year, int month, int day,
                                                    DatePickerDialog.OnDateSetListener listener){
        log("createDatePickerDialog()");
        return new DatePickerDialog(Objects.requireNonNull(getContext())
                , listener, year, month, day);
    }


    public void setEnabledEditTexts(boolean isYes) {
        log("setEnabled( isYes = %s)", isYes);
        mDescriptionEditText.setEnabled(isYes);
        mTaskEndDateTextInputEditText.setEnabled(isYes);
        mTaskStartDateTextInputEditText.setEnabled(isYes);
        mRewardEditText.setEnabled(isYes);
        mClientNameEditText.setEnabled(isYes);
        mClientPhoneEditText.setEnabled(isYes);
    }

    private void addListenersForEditText() {
        log("addListenersForEditText()");
        mDescriptionEditText.addTextChangedListener(this);
        mTaskStartDateTextInputEditText.addTextChangedListener(this);
        mTaskEndDateTextInputEditText.addTextChangedListener(this);
        mRewardEditText.addTextChangedListener(this);
        mClientPhoneEditText.addTextChangedListener(this);
        mClientNameEditText.addTextChangedListener(this);
    }


    public void showTask(Task task) {
        mIdTextView.setText(task.getTaskID());
        mDescriptionEditText.setText(task.getTaskDescription());
        mTaskStartDateTextInputEditText.setText(task.getStartDate());
        mTaskEndDateTextInputEditText.setText(task.getDeadlineDate());
        mRewardEditText.setText(String.format("%s руб.",task.getReward()));
        mContractorNameTextView.setText(task.getContractorName());
        mContractorPhoneTextView.setText(task.getContractorPhone());
        mClientNameEditText.setText(task.getClientName());
        mClientPhoneEditText.setText(task.getClientPhone());
    }


    public void showActionsTaskViews() {
        log("showActionsTaskViews()");
        mCompleteTaskButton.setVisibility( View.VISIBLE);
        mCancelTaskButton.setVisibility(View.VISIBLE);
    }

    public void hideActionsTaskViews() {
        log("hideActionsTaskViews()");
        mCompleteTaskButton.setVisibility( View.GONE);
        mCancelTaskButton.setVisibility(View.GONE);
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

    public void hideBookViews() {
        log("hideBookViews()");
        mBookButton.setVisibility(View.GONE);
    }
    public void showBookViews() {
        log("showBookViews()");
        mBookButton.setVisibility(View.VISIBLE);
    }

    public void hideClientViews(){
        log("hideClientViews()");
        mClientTextView.setVisibility(View.GONE);
        mClientPhoneEditText.setVisibility(View.GONE);
        mClientNameEditText.setVisibility(View.GONE);
    }

    public void showClientViews(){
        log("showClientViews()");
        mClientTextView.setVisibility(View.VISIBLE);
        mClientPhoneEditText.setVisibility(View.VISIBLE);
        mClientNameEditText.setVisibility(View.VISIBLE);
    }

    public void hideContractorViews() {
        log("hideContractorViews()");
        mContractorTextView.setVisibility(View.GONE);
        mContractorNameTextView.setVisibility(View.GONE);
        mContractorPhoneTextView.setVisibility(View.GONE);
    }

    public void showContractorViews() {
        log("showContractorViews()");
        mContractorTextView.setVisibility(View.VISIBLE);
        mContractorNameTextView.setVisibility(View.VISIBLE);
        mContractorPhoneTextView.setVisibility(View.VISIBLE);
    }

    //utils
    private void setCursorPositionForRewardEditText(){
        String text = mRewardEditText.getText().toString();
        int length =
                text.replaceAll("[^0-9]", "").length();
        mRewardEditText.post(()->mRewardEditText.setSelection(0,length));
    }

    @Override
    protected String createTag() {
        return "TaskFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    //TextWatcher listener
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mPresenter.descriptionTaskIsChanged();
    }

    //swipe refresh listener
    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    //setters
    public void setTaskStartDate(String date){
        mTaskStartDateTextInputEditText.setText(date);
    }

    public void setTaskEndDate(String date){
        mTaskEndDateTextInputEditText.setText(date);
    }

    //getters
    public String getDescription() {
        return mDescriptionEditText.getText().toString();
    }

    public String getTaskStartDate() {
        return mTaskStartDateTextInputEditText.getText() == null ?
                null : mTaskStartDateTextInputEditText.getText().toString();
    }

    public String getTaskEndDate() {

        return mTaskEndDateTextInputEditText.getText() == null ?
                null : mTaskEndDateTextInputEditText.getText().toString();
    }

    public String getReward() {
        String reward =  mRewardEditText.getText().toString();
        return reward.replaceAll("[^0-9]", "");
    }

    public String getContractorName() {
        return mContractorNameTextView.getText().toString();
    }

    public String getContractorPhone() {
        return mContractorPhoneTextView.getText().toString();
    }

    public String getTaskId() {
        return mIdTextView.getText().toString();
    }

    public String getClientName() {
        return mClientNameEditText.getText().toString();
    }

    public String getClientPhone() {
        return mClientPhoneEditText.getText().toString();
    }




}
