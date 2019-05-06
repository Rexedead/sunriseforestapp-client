package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.TaskPresenter;


public class TaskFragment extends BaseFragment implements TextWatcher {

    private Task mSingleTask;

    private TaskPresenter mPresenter = TaskPresenter.getInstance();

    private TextView mIdTextView;
    private EditText mDescriptionEditText;
    private EditText mTaskStartDateEditText;
    private EditText mTaskEndDateEditText;
    private EditText mRewardEditText;
    private EditText mClientPhoneEditText;
    private EditText mClientNameEditText;
    private Button mSaveButton;

    private TextView mContractorNameTextView;
    private TextView mContractorPhoneTextView;
    private Button mBookButton;
    private Button mCompleteTask;
    private Button mCancelTask;

    private View.OnClickListener mSaveTaskListener = view -> mPresenter.clickedSaveButton(mSingleTask);
    private View.OnClickListener mBookListener = view -> mPresenter.clickedBookButton();
    private View.OnClickListener mCompleteListener = view -> mPresenter.clickedCompleteButton();
    private View.OnClickListener mCancelListener = view -> mPresenter.clickedCancelButton();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.task_fragment, container, false);
        mIdTextView = view.findViewById(R.id.id_taskFrag_textView);
        mDescriptionEditText = view.findViewById(R.id.description_taskFrag_editText);
        mTaskStartDateEditText = view.findViewById(R.id.start_date_taskFrag_editText);
        mTaskEndDateEditText = view.findViewById(R.id.end_date_taskFrag_editText);
        mRewardEditText = view.findViewById(R.id.reward_taskFrag_editText);
        mClientPhoneEditText = view.findViewById(R.id.client_phone_taskFrag_editText);
        mClientNameEditText = view.findViewById(R.id.client_name_taskFrag_editText);
        mSaveButton = view.findViewById(R.id.save_taskFrag_button);
        mContractorNameTextView = view.findViewById(R.id.contractor_name_taskFrag_textView);
        mContractorPhoneTextView = view.findViewById(R.id.contractor_phone_taskFrag_textView);
        mBookButton = view.findViewById(R.id.book_taskFrag_button);
        mCompleteTask = view.findViewById(R.id.complete_taskFrag_button);
        mCancelTask = view.findViewById(R.id.cancel_taskFrag_button);

        addListenersForEditText();
        hideBottomNavigation();

        return view;

    }

    public String getDescription() {
        return mDescriptionEditText.getText().toString();
    }

    public String getTaskStartDate() {
        return mTaskStartDateEditText.getText().toString();
    }

    public String getTaskEndDate() {
        return mTaskEndDateEditText.getText().toString();
    }

    public String getReward() {
        return mRewardEditText.getText().toString();
    }

    public void setEnabledEditTexts(boolean isYes) {
        log("setEnabled( isYes = %s)", isYes);


        mDescriptionEditText.setEnabled(isYes);
        mTaskEndDateEditText.setEnabled(isYes);
        mTaskStartDateEditText.setEnabled(isYes);
        mRewardEditText.setEnabled(isYes);
        mClientNameEditText.setEnabled(isYes);
        mClientPhoneEditText.setEnabled(isYes);
    }

    private void addListenersForEditText() {
        mSaveButton.setOnClickListener(mSaveTaskListener);
        mBookButton.setOnClickListener(mBookListener);
        //добавляем листенеры для отмены и комплита.. наверно этот метод можно переименовать в addListeners()
        mCompleteTask.setOnClickListener(mCompleteListener);
        mCancelTask.setOnClickListener(mCancelListener);
        mDescriptionEditText.addTextChangedListener(this);
        mTaskStartDateEditText.addTextChangedListener(this);
        mTaskEndDateEditText.addTextChangedListener(this);
        mRewardEditText.addTextChangedListener(this);
        mClientPhoneEditText.addTextChangedListener(this);
        mClientNameEditText.addTextChangedListener(this);
    }


    public void showTask(Task task) {
        log("showTask(task = %s)", task);
        setTask(task);
    }

    private void setTask(Task task) {
        mSingleTask = task;
        mIdTextView.setText(mSingleTask.getTaskID());
        mDescriptionEditText.setText(mSingleTask.getTaskDescription());
        mTaskStartDateEditText.setText(mSingleTask.getCreationDate());
        mTaskEndDateEditText.setText(mSingleTask.getDeadlineDate());
        mRewardEditText.setText(String.valueOf(mSingleTask.getReward()));
        mContractorNameTextView.setText(mSingleTask.getContractorName());
        mContractorPhoneTextView.setText(mSingleTask.getContractorPhone());

        if (mSingleTask.getClient() != null) {
            mClientNameEditText.setText(mSingleTask.getClient().getName());

        }

    }
    public void saveButtonIsVisible(boolean isVisible) {
        mSaveButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    //скрываем забронировать
    //не вижу смысла делать замену кнопки с брони на "завершить", это только запутает
    public void bookButtonIsVisible(boolean isVisible) {
        mBookButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    //отображаем действия
    public void taskActionsIsVisible(boolean isVisible) {
        mCompleteTask.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        mCancelTask.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected String createTag() {
        return "TaskFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
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
        this.saveButtonIsVisible(true);
    }

}
