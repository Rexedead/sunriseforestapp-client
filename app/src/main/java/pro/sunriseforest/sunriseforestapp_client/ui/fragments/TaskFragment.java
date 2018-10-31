package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

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
//
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        TextView taskName = view.findViewById(R.id.task_name_textView);
        taskName.setText(mSingleTask.getName());

        TextView mTaskDate = view.findViewById(R.id.task_date_textView);
        mTaskDate.setText(mSingleTask.getDate());

        CheckBox mTaskIsBooked = view.findViewById(R.id.task_is_booked_checkBox);
        mTaskIsBooked.setChecked(mSingleTask.isBooked());

        TextView text = view.findViewById(R.id.task_text_textView);
        text.setText(mSingleTask.getTextTask());

        TextView address = view.findViewById(R.id.address_text);
        address.setText(mSingleTask.getAddress());

        TextView reward = view.findViewById(R.id.reward_text);
        reward.setText(mSingleTask.getReward()+"руб.");

        return view;

    }
}
