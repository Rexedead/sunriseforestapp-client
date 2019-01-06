package pro.sunriseforest.sunriseforestapp_client.ui.old;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Client;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
//import pro.sunriseforest.sunriseforestapp_client.presenter.NewTaskPresenter;
//import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;
//
//
//public class NewTaskActivity extends AppCompatActivity {
//
//    private NewTaskPresenter mNewTaskPresenter;
//    Calendar mCalendar = Calendar.getInstance();
//
//    public static void startActivity(Context context) {
//        Intent intent = new Intent(context, NewTaskActivity.class);
//        context.startActivity(intent);
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.new_task_fragment);
//
//        final EditText mTaskDescriptionEditText = findViewById(R.id.newTask_description_editText);
//
//        final EditText mTaskRewardEditText = findViewById(R.id.newTask_reward_editText);
//        findViewById(R.id.newTask_startDate_textInputLayout);
//        final TextInputEditText mTaskStartDateEditText = findViewById(R.id.newTask_startDate_TextInputEditText);
//
//        findViewById(R.id.newTask_endDate_textInputLayout);
//        final TextInputEditText mTaskEndDateTextInputEditText = findViewById(R.id.newTask_endDate_TextInputEditText);
//
//        final EditText mTaskClientNameEditText = findViewById(R.id.newTask_clientName_editText);
//        final EditText mTaskClientPhoneEditText = findViewById(R.id.newTask_clientPhone_editText);
//
//
//        Button mAddNewTaskButton = findViewById(R.id.newTask_add_button);
//
//
//        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
//                mTaskClientPhoneEditText,
//                "+7 ([000]) [000]-[00]-[00]",
//                new MaskedTextChangedListener.ValueListener() {
//                    @Override
//                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue) {
//                        Log.d("TAG", extractedValue);
//                        Log.d("TAG", String.valueOf(maskFilled));
//                    }
//                }
//        );
//        mTaskClientPhoneEditText.setHint(listener.placeholder());
//
//
//
//        mNewTaskPresenter = (NewTaskPresenter) PresenterManager.getInstance()
//                .getPresenter(NewTaskPresenter.TAG);
//
//
//        mTaskStartDateEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new DatePickerDialog(NewTaskActivity.this, dpd, mCalendar
//                        .get(Calendar.YEAR),
//                        mCalendar.get(Calendar.MONTH),
//                        mCalendar.get(Calendar.DAY_OF_MONTH))
//                        .show();
//            }
//
//            DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    mCalendar.set(Calendar.YEAR, year);
//                    mCalendar.set(Calendar.MONTH, monthOfYear);
//                    mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    updateLabel();
//                }
//            };
//
//            void updateLabel() {
//                String myFormat = "dd/MM/yyyy";
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                mTaskStartDateEditText.setText(sdf.format(mCalendar.getTime()));
//                mTaskEndDateTextInputEditText.setText(sdf.format(mCalendar.getTime()));
//
//            }
//
//        });
//
//
//        mTaskEndDateTextInputEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(NewTaskActivity.this, dpd, mCalendar
//                        .get(Calendar.YEAR),
//                        mCalendar.get(Calendar.MONTH),
//                        mCalendar.get(Calendar.DAY_OF_MONTH))
//                        .show();
//            }
//
//            DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    mCalendar.set(Calendar.YEAR, year);
//                    mCalendar.set(Calendar.MONTH, monthOfYear);
//                    mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    updateLabel();
//                }
//            };
//
//            void updateLabel() {
//                String myFormat = "dd/MM/yyyy";
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                mTaskEndDateTextInputEditText.setText(sdf.format(mCalendar.getTime()));
//            }
//        });
//
//
//        mAddNewTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                boolean checkForEmptyField = validateNotEmpty(new EditText[]{
//                        mTaskDescriptionEditText,
//                        mTaskRewardEditText,
//                        mTaskEndDateTextInputEditText
//                });
//
//
//                if (!checkForEmptyField) {
//                    Snackbar.make(v, "Неверно заполнены поля", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                } else {
//                    mNewTaskPresenter.addNewClient(new Client(
//                            mTaskClientNameEditText.getText().toString(),
//                            mTaskClientPhoneEditText.getText().toString()));
//
//                    mNewTaskPresenter.addTask(new Task(
//                            mTaskDescriptionEditText.getText().toString(),
//                            Calendar.getInstance().getTime().toString(),
//                            Objects.requireNonNull(mTaskStartDateEditText.getText()).toString(),
//                            Objects.requireNonNull(mTaskEndDateTextInputEditText.getText()).toString(),
//                            (byte) 101,
//                            Integer.parseInt(mTaskRewardEditText.getText().toString())
//                            ));
////                    finish();
//                }
//            }
//        });
//
//
//    }
//
//
//    private boolean validateNotEmpty(EditText[] fields) {
//        for (EditText currentField : fields) {
//            if (currentField.getText().toString().length() <= 0) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    public void showTaskDeskActivity() {
//        TaskDeskActivity.startActivity(this);
//        showToast("Задание добавлено");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mNewTaskPresenter.isActivityUnBunding()) {
//            mNewTaskPresenter.bindView(this);
//        }
//        mNewTaskPresenter.update();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mNewTaskPresenter.unBindActivity();
//    }
//
//    public void showError(String msg) {
//        showToast(msg);
//    }
//
//    private void showToast(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }
//}
