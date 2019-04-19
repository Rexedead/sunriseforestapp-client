package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.DeskPresenter;


public class DeskFragment extends BaseFragment {
    private static final String ARG_JSON_TASKS = "arg_json_tasks";

    private List<Task> mTaskList = new ArrayList<>();
    private DeskPresenter mPresenter = DeskPresenter.getInstance();
    private RecycleTaskAdapter mRecycleTaskAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mNewTaskFloatingActionButton;

    private View.OnClickListener mOnClickListenerNewTaskFloatingActionButton = view ->{
      mPresenter.clickedNewTask();
    };


    @Override
    protected String createTag() {
        return "DeskFragment";
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.update();
    }

    public void showTasks(List<Task> tasks){
        mTaskList.clear();
        mTaskList.addAll(tasks);
        mRecycleTaskAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.desk_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.desk_recyclerView);
        mRecycleTaskAdapter = new RecycleTaskAdapter(mTaskList);
        mNewTaskFloatingActionButton = view.findViewById(R.id.fab);
        mNewTaskFloatingActionButton.setOnClickListener(mOnClickListenerNewTaskFloatingActionButton);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecycleTaskAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                mRecyclerView));

        showBottomNavigation();

        return view;
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
                        log("onLongPress(). Position = " + position);
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            log("RecyclerTouchListener onInterceptTouchEvent()");
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && gestureDetector.onTouchEvent(e)){
                int position = rv.getChildAdapterPosition(child);
            mPresenter.clickedSelectedTask(position);
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            log("RecyclerTouchListener onTouchEvent()");
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            log("RecyclerTouchListener onRequestDisallowInterceptTouchEvent" +
                            "(disallowIntercept=%s)" ,disallowIntercept);

        }
    }

}







