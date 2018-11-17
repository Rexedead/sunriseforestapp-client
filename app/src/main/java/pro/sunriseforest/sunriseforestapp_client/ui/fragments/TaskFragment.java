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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_fragment, container, false);

//        TextView taskName = view.findViewById(R.id.task_name_textView);

        TextView mTaskDate = view.findViewById(R.id.task_date_textView);
        mTaskDate.setText(mSingleTask.getCreationDate());

//        CheckBox mTaskIsBooked = view.findViewById(R.id.task_is_booked_checkBox);
//        mTaskIsBooked.setChecked(mSingleTask.getStatus());

        TextView text = view.findViewById(R.id.task_text_textView);
        text.setText(mSingleTask.getTaskDescription());

        TextView deadline = view.findViewById(R.id.deadline_text_textView);
        deadline.setText(mSingleTask.getDeadlineDate());

        TextView reward = view.findViewById(R.id.reward_text_textView);
        reward.setText(mSingleTask.getReward()+"руб.");

        TextView clientName = view.findViewById(R.id.client_text_textView);

        if(mSingleTask.getClient()!=null){
            clientName.setText(mSingleTask.getClient().getName());

        }

        TextView contractor = view.findViewById(R.id.contractor_text_textView);

        if(mSingleTask.getContractor() != null){
            contractor.setText(mSingleTask.getContractor().getName());
        }

        return view;

    }
}
