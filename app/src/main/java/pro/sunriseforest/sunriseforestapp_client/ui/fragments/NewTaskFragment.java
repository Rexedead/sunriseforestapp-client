package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import java.util.Calendar;
import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.NewTaskPresenter;

public class NewTaskFragment extends BaseFragment {

    private NewTaskPresenter mPresenter = NewTaskPresenter.getInstance();

    private EditText mTaskDescriptionEditText;
    private EditText mTaskRewardEditText;

    private TextInputEditText mTaskStartDateInputEditText;

    private TextInputEditText mTaskEndDateTextInputEditText;

    private EditText mTaskClientNameEditText;
    private EditText mTaskClientPhoneEditText;

    private Button mAddNewTaskButton;

    private DatePickerDialog.OnDateSetListener mOnDateEndSetListener =
            (dp, y, m, d) ->
                    mPresenter.setEndDate(y, m, d);

    private DatePickerDialog.OnDateSetListener mOnStartDateSetListener =
            (dp, y, m, d) ->
            mPresenter.setStartDate(y, m, d);

    private View.OnClickListener mTaskStartDateOnClickListener = view -> mPresenter.clickedTaskStartDate();

    private View.OnClickListener mTaskEndDateOnClickListener = view -> mPresenter.clickedTaskEndDate();

    private View.OnClickListener mAddTaskListener = view -> mPresenter.clickedAddNewTask(getTask());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.new_task_fragment, container, false);

        mTaskDescriptionEditText = view.findViewById(R.id.description_newTask_editText);
        mTaskRewardEditText = view.findViewById(R.id.reward_newTask_editText);
        mTaskStartDateInputEditText = view.findViewById(R.id.start_date_newTask_TextInputEditText);
        mTaskEndDateTextInputEditText = view.findViewById(R.id.end_date_newTask_TextInputEditText);
        mTaskClientNameEditText = view.findViewById(R.id.client_name_newTask_editText);
        mTaskClientPhoneEditText = view.findViewById(R.id.client_phone_newTask_editText);
        mAddNewTaskButton = view.findViewById(R.id.add_newTask_button);

        // set mask hint on PhoneEditText
        final MaskedTextChangedListener maskedPhoneTextChangedListener = MaskedTextChangedListener
                .Companion
                .installOn(
                mTaskClientPhoneEditText,
                "+7 ([000]) [000]-[00]-[00]",
                (maskFilled, extractedValue) -> {}
        );

        mTaskRewardEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                String reward = mTaskRewardEditText.getText().toString();
                reward = reward.replaceAll("[^0-9]", "");
                mTaskRewardEditText.setText(reward);
            }else{
                mTaskRewardEditText.append(" руб.");
            }
        });


        mTaskClientPhoneEditText.setHint(maskedPhoneTextChangedListener.placeholder());

//        mTaskRewardEditText.setHint(maskedRewardTextChangedListener.placeholder());
        mTaskStartDateInputEditText.setOnClickListener(mTaskStartDateOnClickListener);
        mTaskEndDateTextInputEditText.setOnClickListener(mTaskEndDateOnClickListener);
        mAddNewTaskButton.setOnClickListener(mAddTaskListener);
        log(mAddNewTaskButton.toString());
        return view;

    }

//todo -> presenter
    public void updateStartDateEdit(String date){
        log("updateDateEdits()");
        mTaskStartDateInputEditText.setText(date);
        // серьезный костыль приводящий к багу
 /*       дата окончания = дата старта = однодневное задание,
         при необходимости меняем окончание вручную*/
        Editable endData = mTaskEndDateTextInputEditText.getText();
        if(TextUtils.isEmpty(endData)) mTaskEndDateTextInputEditText.setText(date);

    }

    public void updateEndDateEdit(String date){
        log("updateDateEdits()");
        mTaskEndDateTextInputEditText.setText(date);
    }


    public void showStartDatePickerDialog(int year, int month, int day ){
        log("showStartDatePickerDialog()");
        createDatePickerDialog(year, month, day,mOnStartDateSetListener).show();
    }

    public void showEndDatePickerDialog(int year, int month, int day){
        log("showEndDatePickerDialog()");
        createDatePickerDialog(year, month, day, mOnDateEndSetListener).show();
    }

    private DatePickerDialog createDatePickerDialog(int year, int month, int day,
                                                    DatePickerDialog.OnDateSetListener listener){
        log("createDatePickerDialog()");
        return new DatePickerDialog(Objects.requireNonNull(getContext())
                , listener, year, month, day);
    }


    @Override
    protected String createTag() {
        return "NewTaskFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    private Task getTask(){
        // считываем с полей и формируем таск

        Task task = new Task();
        task.setTaskDescription(mTaskDescriptionEditText.getText() == null ?
                null : mTaskDescriptionEditText.getText().toString());

        task.setCreationDate(Calendar.getInstance().getTime().toString());
        task.setStartDate(mTaskStartDateInputEditText.getText() == null ?
                null : mTaskStartDateInputEditText.getText().toString());

        task.setDeadlineDate(
                mTaskEndDateTextInputEditText.getText() == null ?
                        null : mTaskEndDateTextInputEditText.getText().toString());
        task.setStatus((byte) 101);
        task.setReward(getReward());

        task.setClientName( mTaskClientNameEditText.getText()==null ?
                null : mTaskClientNameEditText.getText().toString());

        task.setClientPhone(mTaskClientPhoneEditText.getText()==null ?
                null : mTaskClientPhoneEditText.getText().toString());

        return task;
    }

    private int getReward(){
        String rewardString = mTaskRewardEditText.getText()
                .toString()
                .replaceAll("[^0-9]", "");

        if(rewardString.equals("")) return 0;
        return Integer.parseInt(rewardString);
    }

}
