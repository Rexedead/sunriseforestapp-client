package pro.sunriseforest.sunriseforestapp_client.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.date.DataBaseHelper;
import pro.sunriseforest.sunriseforestapp_client.models.Task;


public class NewTaskActivity extends AppCompatActivity {

    private DataBaseHelper mServerHelper;
    Calendar dateAndTime = Calendar.getInstance();
    Button mTaskDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task_activity);

        TextView mTaskId = findViewById(R.id.newTask_id_textView);

        final EditText mClientName = findViewById(R.id.newTask_clientName_editText);
        final EditText mTaskDescription = findViewById(R.id.newTask_text_editText);
        final EditText mTaskAddress = findViewById(R.id.newTask_address_editText);
        final EditText mTaskReward = findViewById(R.id.newTask_reward_editText);
        mTaskDeadline = findViewById(R.id.newTask_deadline_button);
        Button mAddNewTask = findViewById(R.id.newTask_add_button);

        mServerHelper = DataBaseHelper.getInstance();
        mTaskId.setText(String.valueOf(mServerHelper.getTasks().size() + 1));


        mTaskDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });


        mAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkForEmptyField = validateNotEmpty(new EditText[]{
                        mClientName,
                        mTaskDescription,
                        mTaskAddress,
                        mTaskReward,
                });


                if (!checkForEmptyField) {
                    Snackbar.make(v, "Неверно заполнены поля", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    mServerHelper.addNewTask(new Task(
                            mServerHelper.getTasks().size() + 1,
                            mClientName.getText().toString(),
                            (byte)101,
//                          mTaskAddress.getText().toString(),
                            Calendar.getInstance().getTime().toString(),
                            Integer.parseInt(mTaskReward.getText().toString())
//                          mTaskDeadline.getText().toString(),
                    ));
                    finish();
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
            mTaskDeadline.setText(getString(R.string.date_pick, dayOfMonth, monthOfYear, year));

        }
    };


}
