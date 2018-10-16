package pro.sunriseforest.sunriseforestapp_client.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;


public class TaskFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView mTaskData, mTaskDate;
    private CheckBox mTaskAccepted;
    private static Task mSingleTask;


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(Task t) {
        mSingleTask = t;
        return new TaskFragment();
    }


    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        mTaskData = view.findViewById(R.id.task_data_textView);
        mTaskDate = view.findViewById(R.id.task_date_textView);
        mTaskDate = view.findViewById(R.id.task_date_textView);
        final CheckBox mTaskAccepted = view.findViewById(R.id.task_accepted_checkBox);
        mTaskData.setText(mSingleTask.getTextTask());
        mTaskDate.setText(mSingleTask.getDate());
        mTaskAccepted.setChecked(mSingleTask.isBooked());
        return view;

    }
}
