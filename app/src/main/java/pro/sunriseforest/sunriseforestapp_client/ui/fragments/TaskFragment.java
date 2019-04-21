package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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


public class TaskFragment extends BaseFragment {
    private Task mSingleTask;
    private static final String ARG_JSON_TASK = "ARG_JSON_TASK";

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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.task_fragment, container, false);
// TODO названия id привести к виду : название_переменной_[имяФрагмена]_тип
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


        mSaveButton.setOnClickListener(v -> mPresenter.clickedSaveButton());

        hideBottomNavigation();

        return view;

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

    public void hideSaveButton() {
        //...
    }

    public void showSaveButton() {
        //...
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

        String reward = mSingleTask.getReward() + " \u20BD";
        mRewardEditText.setText(reward);

        if (mSingleTask.getClient() != null) {
            mClientNameEditText.setText(mSingleTask.getClient().getName());

        }


    }


    @Override
    protected String createTag() {
        return "SplashFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }


}
