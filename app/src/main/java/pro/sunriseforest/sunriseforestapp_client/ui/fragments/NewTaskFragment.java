package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
//import pro.sunriseforest.sunriseforestapp_client.presenters.old.AppPresenter;

public class NewTaskFragment extends BaseFragment {

    Calendar mCalendar = Calendar.getInstance();


    public NewTaskFragment() {
        // Required empty public constructor
    }


    @Override
    protected String createTag() {
        return "NewTaskFragment";
    }

    @Override
    protected BasePresenter getPresenter() {
        return new BasePresenter() {
            @Override
            protected String getTAG() {
                return "хузентер2";
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_task_fragment, container, false);

        final EditText mTaskDescriptionEditText = view.findViewById(R.id.newTask_description_editText);

        final EditText mTaskRewardEditText = view.findViewById(R.id.newTask_reward_editText);
        view.findViewById(R.id.newTask_startDate_textInputLayout);
        final TextInputEditText mTaskStartDateEditText = view.findViewById(R.id.newTask_startDate_TextInputEditText);

        view.findViewById(R.id.newTask_endDate_textInputLayout);
        final TextInputEditText mTaskEndDateTextInputEditText = view.findViewById(R.id.newTask_endDate_TextInputEditText);

        final EditText mTaskClientNameEditText = view.findViewById(R.id.newTask_clientName_editText);
        final EditText mTaskClientPhoneEditText = view.findViewById(R.id.newTask_clientPhone_editText);


        Button mAddNewTaskButton = view.findViewById(R.id.newTask_add_button);

//Это все надо выносить в презентер, что бы работало и для нового таска и для редактирования существующего
        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                mTaskClientPhoneEditText,
                "+7 ([000]) [000]-[00]-[00]",
                (maskFilled, extractedValue) -> {
                    log("onTextChanged " + extractedValue);
                    log("onTextChanged " + String.valueOf(maskFilled));
                }
        );
        mTaskClientPhoneEditText.setHint(listener.placeholder());


        mTaskStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("cl: Select Start Date onClick()");
                new DatePickerDialog(Objects.requireNonNull(getContext()), dpd, mCalendar
                        .get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

            DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mCalendar.set(Calendar.YEAR, year);
                    mCalendar.set(Calendar.MONTH, monthOfYear);
                    mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };

            void updateLabel() {
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                mTaskStartDateEditText.setText(sdf.format(mCalendar.getTime()));
                mTaskEndDateTextInputEditText.setText(sdf.format(mCalendar.getTime()));

            }

        });

            mTaskEndDateTextInputEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    log("cl: Select End Date onClick()");
                    new DatePickerDialog(Objects.requireNonNull(getContext()), dpd, mCalendar
                            .get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH))
                            .show();
                }

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mCalendar.set(Calendar.YEAR, year);
                        mCalendar.set(Calendar.MONTH, monthOfYear);
                        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                };

                void updateLabel() {
                    String myFormat = "dd/MM/yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    mTaskEndDateTextInputEditText.setText(sdf.format(mCalendar.getTime()));
                }
            });


        mAddNewTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    log("cl: AddNewTaskButton onClick()");
                    boolean checkForEmptyField = validateNotEmpty(new EditText[]{
                            mTaskDescriptionEditText,
                            mTaskRewardEditText,
                            mTaskEndDateTextInputEditText
                    });


                    if (!checkForEmptyField) {
                        Snackbar.make(v, "Неверно заполнены поля", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
//                        AppPresenter.getInstance().addTask(
                        new Task(
                                mTaskDescriptionEditText.getText().toString(),
                                Calendar.getInstance().getTime().toString(),
                                Objects.requireNonNull(mTaskStartDateEditText.getText()).toString(),
                                Objects.requireNonNull(mTaskEndDateTextInputEditText.getText()).toString(),
                                (byte) 101,
                                Integer.parseInt(mTaskRewardEditText.getText().toString()),
                                null,
                                null
                        );
                    }
                }
            });

        hideBottomNavigation();

        return view;

    }

    private boolean validateNotEmpty(EditText[] fields) {
        for (EditText currentField : fields) {
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

}
