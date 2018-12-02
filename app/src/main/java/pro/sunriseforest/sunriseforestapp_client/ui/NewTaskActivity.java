package pro.sunriseforest.sunriseforestapp_client.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenter.NewTaskPresenter;
import pro.sunriseforest.sunriseforestapp_client.presenter.PresenterManager;


public class NewTaskActivity extends AppCompatActivity {

    private NewTaskPresenter mNewTaskPresenter;
    Calendar dateAndTime = Calendar.getInstance();
    Button mTaskDeadlineButton;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NewTaskActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);

        TextView mTaskId = findViewById(R.id.newTask_id_textView);

        final EditText mTaskDescriptionEditText = findViewById(R.id.newTask_text_editText);
        final EditText mTaskRewardEditText = findViewById(R.id.newTask_reward_editText);
        mTaskDeadlineButton = findViewById(R.id.newTask_deadline_button);
        Button mAddNewTaskButton = findViewById(R.id.newTask_add_button);

        mTaskId.setText("TODO");

        mNewTaskPresenter = (NewTaskPresenter) PresenterManager.getInstance()
                .getPresenter(NewTaskPresenter.TAG);


        mTaskDeadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });


        mAddNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkForEmptyField = validateNotEmpty(new EditText[]{
                        mTaskDescriptionEditText,
                        mTaskRewardEditText,
                });


                if (!checkForEmptyField) {
                    Snackbar.make(v, "Неверно заполнены поля", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    mNewTaskPresenter.addNewTask(new Task(
                            mTaskDescriptionEditText.getText().toString(),
                            Calendar.getInstance().getTime().toString(),
                            mTaskDeadlineButton.getText().toString(),
                            Calendar.getInstance().getTime().toString(),
                            (byte)101,
                            Integer.parseInt(mTaskRewardEditText.getText().toString())
                    ));
//                    finish();
                }
            }
        });


    }

    private boolean validateNotEmpty(EditText[] fields) {
        for (EditText currentField : fields) {
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }


    private void setDate() {
        new DatePickerDialog(NewTaskActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mTaskDeadlineButton.setText(getString(R.string.date_pick, dayOfMonth, monthOfYear, year));

        }
    };


    public void showTaskDeskActivity(){
        TaskDeskActivity.startActivity(this);
        showToast("Задание добавлено");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mNewTaskPresenter.isActivityUnBunding()){
            mNewTaskPresenter.bindActivity(this);
        }
        mNewTaskPresenter.update();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNewTaskPresenter.unBindActivity();
    }

    public void showError(String msg){
        showToast(msg);
    }
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
