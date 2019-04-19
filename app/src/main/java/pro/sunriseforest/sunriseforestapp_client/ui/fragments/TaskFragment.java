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
import pro.sunriseforest.sunriseforestapp_client.ui.AppActivity;


public class TaskFragment extends BaseFragment {
    private Task mSingleTask;
    private static final String ARG_JSON_TASK = "ARG_JSON_TASK";

    private TaskPresenter mPresenter = TaskPresenter.getInstance();

    private TextView mIdTextView;
    private EditText mDescriptionEditText;
    private EditText mTaskDateEditText;
    private EditText mDeadlineEditText;
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

        mIdTextView = view.findViewById(R.id.singleTask_id_textView); // TODO названия id привести к виду : название_переменной_[имяФрагмена]_тип
        mDescriptionEditText = view.findViewById(R.id.singleTask_description_editText);// в ресурсах со строками тоже названия привести в порядок, но там префиксы с именем фрагмента и типом использовать только когда это необходимо, чтобы избежать конфликт имен
        mTaskDateEditText = view.findViewById(R.id.singleTask_startDate_TextInputEditText);// чет треш
        mDeadlineEditText = view.findViewById(R.id.singleTask_endDate_TextInputEditText);
        mRewardEditText = view.findViewById(R.id.singleTask_reward_editText);
        mClientPhoneEditText = view.findViewById(R.id.singleTask_clientPhone_editText);
        mClientNameEditText = view.findViewById(R.id.singleTask_clientName_editText);
        mSaveButton = view.findViewById(R.id.save_taskFrag_button);


        mContractorNameTextView = view.findViewById(R.id.singleTask_contractorName_Textview);
        mContractorPhoneTextView = view.findViewById(R.id.singleTask_contractorPhone_textView);
        mBookButton = view.findViewById(R.id.singleTask_book_button);


        mSaveButton.setOnClickListener(v -> mPresenter.clickedSaveButton());

        hideBottomNavigation();

        return view;

    }



    public void setEnabledEditTexts(boolean isYes){
        log("setEnabled( isYes = %s)", isYes);


        mDescriptionEditText.setEnabled(isYes);
        mDeadlineEditText.setEnabled(isYes);
        mTaskDateEditText.setEnabled(isYes);
        mRewardEditText.setEnabled(isYes);
        mClientNameEditText.setEnabled(isYes);
        mClientPhoneEditText.setEnabled(isYes);
    }

    public void hideSaveButton(){
        //...
    }

    public void showSaveButton(){
        //...
    }




    public void showTask(Task task){
        log("showTask(task = %s)", task);
        setTask(task);
    }

    private void setTask(Task task){
        mSingleTask = task;

        mIdTextView.setText(mSingleTask.getTaskID());

        mDescriptionEditText.setText(mSingleTask.getTaskDescription());

        mTaskDateEditText.setText(mSingleTask.getCreationDate());

        mDeadlineEditText.setText(mSingleTask.getDeadlineDate());

        String reward = mSingleTask.getReward()+" \u20BD";
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
