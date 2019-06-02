package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.NotificationsPresenter;

public class NotificationsFragment extends NavigatedFragment
implements RecycleNotificationAdapter.NotificationClickListener {

    private static final int ITEM_ON_NAV = 2;
    private static final String TAG = "NotificationsFragment";

    private NotificationsPresenter mPresenter = NotificationsPresenter.getInstance();

    //todo временная кнопка
    private Button mTurnNotificationButton;
    //todo временная кнопка
    private Button mDeleteNotificationsButton;

    private RecyclerView mNotificationsRecyclerView;

    private RecycleNotificationAdapter mAdapter;


    public NotificationsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notifications_fragment, container, false);
        mTurnNotificationButton = view.findViewById(R.id.turn_notifications);
        mTurnNotificationButton.setOnClickListener( v -> mPresenter.clickedTurnNotification());

        mDeleteNotificationsButton = view.findViewById(R.id.delete_notifications);
        mDeleteNotificationsButton.setOnClickListener(v -> mPresenter.clickedDeleteAllNotifications());

        mNotificationsRecyclerView = view.findViewById(R.id.notifications_recyclerView);
        mAdapter = new RecycleNotificationAdapter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mNotificationsRecyclerView.setLayoutManager(layoutManager);
        mNotificationsRecyclerView.setAdapter(mAdapter);
        mNotificationsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        showNotificationsAreWorks(mPresenter.notificationsAreWorks());

        showBottomNavigation();
        return view;
    }

    public void updateNotifications(List<SunriseNotification> sunriseNotifications){
        mAdapter.addSunriseNotifications(sunriseNotifications, true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getItemOnNavigationMenu() {
        return ITEM_ON_NAV;
    }

    @Override
    protected String createTag() {
        return TAG;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    // todo временный метод
    public void showNotificationsAreWorks(boolean areWorks) {
        if(areWorks){
            mTurnNotificationButton.setText("Выключить уведомления");
        }else{
            mTurnNotificationButton.setText("Включить уведомления");
        }
    }

    @Override
    public void onClick(int position) {
        mPresenter.clickedSelectedNotification(position);
    }

}
