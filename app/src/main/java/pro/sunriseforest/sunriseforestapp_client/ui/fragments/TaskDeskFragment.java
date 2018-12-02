package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;


public class TaskDeskFragment extends Fragment  {
    private List<Task> mTaskList = new ArrayList<>();
    private OnClickSingleRowListener mListener;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_JSON_TASKS = "arg_json_tasks";


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

        try {
            mListener = (OnClickSingleRowListener)getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(Objects.requireNonNull(getActivity()).toString() + " must implement iSingleRowClickedListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.taskdesk_fragment, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.taskdesk_recyclerView);
        RecycleTaskAdapter mRecycleTaskAdapter = new RecycleTaskAdapter(mTaskList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecycleTaskAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                mRecyclerView));
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnClickSingleRowListener {
        void onClicked(View singleRowView, int position);
    }



    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final RecyclerView recycleView ){

            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null){
                        int position = recycleView.getChildAdapterPosition(child);
                        Log.i("TaskDeskFragment", "onLongPress(). Position = " + position);
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && gestureDetector.onTouchEvent(e)){
                int position = rv.getChildAdapterPosition(child);
                mListener.onClicked(child,position);
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}







