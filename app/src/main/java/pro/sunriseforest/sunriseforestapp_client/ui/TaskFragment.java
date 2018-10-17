package pro.sunriseforest.sunriseforestapp_client.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;


public class TaskFragment extends Fragment {
    private static Task mSingleTask;


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(Task t) {
        mSingleTask = t;
        return new TaskFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        TextView mTaskData = view.findViewById(R.id.task_data_textView);
        TextView mTaskDate = view.findViewById(R.id.task_date_textView);
        final CheckBox mTaskAccepted = view.findViewById(R.id.task_accepted_checkBox);
        mTaskData.setText(mSingleTask.getTextTask());
        mTaskDate.setText(mSingleTask.getDate());
        mTaskAccepted.setChecked(mSingleTask.isBooked());
        return view;

    }
}
