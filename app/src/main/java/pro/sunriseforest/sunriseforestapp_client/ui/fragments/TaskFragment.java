package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.ui.NewTaskActivity;
import pro.sunriseforest.sunriseforestapp_client.ui.TaskDeskActivity;


public class TaskFragment extends Fragment {
    private Task mSingleTask;
    private static final String ARG_JSON_TASK = "ARG_JSON_TASK";


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(String jsonTask) {
        Bundle args = new Bundle();
        args.putString(ARG_JSON_TASK, jsonTask);
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            String jsonTasks = getArguments().getString(ARG_JSON_TASK);
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(Task.class);
            JsonAdapter<Task> jsonAdapter = moshi.adapter(type);
            try {
                assert jsonTasks != null;
                mSingleTask = jsonAdapter.fromJson(jsonTasks);
            } catch (IOException e) {
                Log.e("TaskDeskFragment", "не удалось перевести json в лист тасков в onCreate()");
            }
        }


        View view = inflater.inflate(R.layout.task_fragment, container, false);

        TextView mIdTextView = view.findViewById(R.id.singleTask_id_textView);
        mIdTextView.setText(mSingleTask.getTaskID());

        final EditText mDescriptionEditText = view.findViewById(R.id.singleTask_description_editText);
        mDescriptionEditText.setText(mSingleTask.getTaskDescription());

        final EditText mTaskDateEditText = view.findViewById(R.id.singleTask_startDate_TextInputEditText);
        mTaskDateEditText.setText(mSingleTask.getCreationDate());

        final EditText mDeadlineEditText = view.findViewById(R.id.singleTask_endDate_TextInputEditText);
        mDeadlineEditText.setText(mSingleTask.getDeadlineDate());

        final EditText mRewardEditText = view.findViewById(R.id.singleTask_reward_editText);
        mRewardEditText.setText(mSingleTask.getReward() + getString(R.string.ruble));


        final EditText mClientPhoneEditText = view.findViewById(R.id.singleTask_clientPhone_editText);
//        mClientPhoneEditText.setText(mSingleTask.getClient().getPhoneNumber());

        final EditText mClientNameEditText = view.findViewById(R.id.singleTask_clientName_editText);
        if (mSingleTask.getClient() != null) {
            mClientNameEditText.setText(mSingleTask.getClient().getName());

        }

        TextView mContractorNameTextView = view.findViewById(R.id.singleTask_contractorName_Textview);
        TextView mContractorPhoneTextView = view.findViewById(R.id.singleTask_contractorPhone_textView);

        if (mSingleTask.getContractor() != null) {
            mContractorNameTextView.setText(mSingleTask.getContractor().getName());
        }
        Button mBookButton = view.findViewById(R.id.singleTask_book_button);
        final Button mEnableTaskEditButton = view.findViewById(R.id.singleTask_editTask_button);

        mDescriptionEditText.setEnabled(false);
        mDeadlineEditText.setEnabled(false);
        mTaskDateEditText.setEnabled(false);
        mRewardEditText.setEnabled(false);
        mClientNameEditText.setEnabled(false);
        mClientPhoneEditText.setEnabled(false);



        mEnableTaskEditButton.setOnClickListener(new View.OnClickListener() {
            private boolean edit = false;

            @Override
            public void onClick(View v) {
                if (edit) {
                    mEnableTaskEditButton.setText("Изменить");
                    mDescriptionEditText.setEnabled(false);
                    mDeadlineEditText.setEnabled(false);
                    mTaskDateEditText.setEnabled(false);
                    mRewardEditText.setEnabled(false);
                    mClientNameEditText.setEnabled(false);
                    mClientPhoneEditText.setEnabled(false);
                    edit = false;
                } else {
                    mEnableTaskEditButton.setText("Сохранить");
                    mDescriptionEditText.setEnabled(true);
                    mDeadlineEditText.setEnabled(true);
                    mTaskDateEditText.setEnabled(true);
                    mRewardEditText.setEnabled(true);
                    mClientNameEditText.setEnabled(true);
                    mClientPhoneEditText.setEnabled(true);
                    edit = true;
                }


            }
        });


        return view;

    }
}
