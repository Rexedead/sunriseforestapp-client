package pro.sunriseforest.sunriseforestapp_client.ui.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.DeskPresenter;


public class DeskFragment extends NavigatedFragment
        implements RecycleTaskAdapter.TaskClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int ITEM_ON_NAV = 0;

    private DeskPresenter mPresenter = DeskPresenter.getInstance();
    private RecycleTaskAdapter mRecycleTaskAdapter = new RecycleTaskAdapter(this);
    private FloatingActionButton mNewTaskFloatingActionButton;

    private View.OnClickListener mOnClickListenerNewTaskFloatingActionButton =
            view -> mPresenter.clickedNewTask();
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected String createTag() {
        return "DeskFragment";
    }


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }


    public void showTasks(List<Task> tasks){
        mRecycleTaskAdapter.addTasks(tasks, true);
    }

    public void showFab(){
        mNewTaskFloatingActionButton.show();

    }

    public void showLoading(){
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void hideLoading(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void hideFab(){
        mNewTaskFloatingActionButton.hide();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.desk_fragment, container, false);

        mNewTaskFloatingActionButton = view.findViewById(R.id.fab);
        mNewTaskFloatingActionButton.setOnClickListener(mOnClickListenerNewTaskFloatingActionButton);
        RecyclerView recyclerView = view.findViewById(R.id.desk_recyclerView);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mRecycleTaskAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(SunriseForestApp.getAppContext()
                ,DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    public int getItemOnNavigationMenu() {
        return ITEM_ON_NAV;
    }

    @Override
    public void onClick(int id) {
        mPresenter.clickedSelectedTask(id);
    }


    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }
}
