package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;


public class TaskFragment extends Fragment {
    private List<Task> mSingleTask = new ArrayList<>();
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
            Type type = Types.newParameterizedType(List.class, Task.class);
            JsonAdapter<List> jsonAdapter = moshi.adapter(type);
            try {
                mSingleTask = jsonAdapter.fromJson(jsonTasks);
            } catch (IOException e) {
                Log.e("TaskDeskFragment", "не удалось перевести json в лист тасков в onCreate()");
            }
        }


        View view = inflater.inflate(R.layout.task_fragment, container, false);

        TextView mTaskDate = view.findViewById(R.id.task_date_textView);
        mTaskDate.setText(mSingleTask.get(0).getCreationDate());

        TextView text = view.findViewById(R.id.task_text_textView);
        text.setText(mSingleTask.get(0).getTaskDescription());

        TextView deadline = view.findViewById(R.id.deadline_text_textView);
        deadline.setText(mSingleTask.get(0).getDeadlineDate());

        TextView reward = view.findViewById(R.id.reward_text_textView);
        reward.setText(mSingleTask.get(0).getReward()+" \u20BD");

        TextView clientName = view.findViewById(R.id.client_text_textView);

        if(mSingleTask.get(0).getClient()!=null){
            clientName.setText(mSingleTask.get(0).getClient().getName());

        }

        TextView contractor = view.findViewById(R.id.contractor_text_textView);

        if(mSingleTask.get(0).getContractor() != null){
            contractor.setText(mSingleTask.get(0).getContractor().getName());
        }

        return view;

    }
}
