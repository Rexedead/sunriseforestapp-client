package pro.sunriseforest.sunriseforestapp_client.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;


public class TaskDeskFragment extends Fragment implements ItemClickListener {
    private List<Task> mTaskList = new ArrayList<>();


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_JSON_TASKS = "arg_json_tasks";


    private OnFragmentInteractionListener mListener;


    public TaskDeskFragment() {
        // Required empty public constructor
    }

    public static TaskDeskFragment newInstance(String jsonListTask) {
        Bundle args = new Bundle();
        args.putString(ARG_JSON_TASKS, jsonListTask);
        TaskDeskFragment fragment = new TaskDeskFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonTasks = getArguments().getString(ARG_JSON_TASKS);
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, Task.class);
            JsonAdapter<List> jsonAdapter = moshi.adapter(type);
            try {
                mTaskList = jsonAdapter.fromJson(jsonTasks);
            } catch (IOException e) {
                Log.e("TaskDeskFragment", "не удалось перевести json в лист тасков в onCreate()");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.taskdesk_fragment, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.taskdesk_recyclerView);
        RecycleTaskAdapter mRecycleTaskAdapter = new RecycleTaskAdapter(mTaskList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecycleTaskAdapter);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view, int position) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}




