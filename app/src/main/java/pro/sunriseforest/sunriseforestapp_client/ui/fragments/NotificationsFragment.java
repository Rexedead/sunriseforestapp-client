package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Notification;
import pro.sunriseforest.sunriseforestapp_client.presenters.BasePresenter;
import pro.sunriseforest.sunriseforestapp_client.presenters.NotificationsPresenter;

public class NotificationsFragment extends NavigatedFragment {

    private static final int ITEM_ON_NAV = 2;
    private static final String TAG = "NotificationsFragment";

    private NotificationsPresenter mPresenter = NotificationsPresenter.getInstance();

    //todo временная кнопка
    private Button mTurnNotificationButton;
    private View.OnClickListener mTurnNotificationListener = view -> mPresenter.clickedTurnNotification();

    private RecyclerView mNotificationsRecyclerView;

    private RecycleNotificationAdapter mAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notifications_fragment, container, false);
        mTurnNotificationButton = view.findViewById(R.id.turn_notifications);
        mTurnNotificationButton.setOnClickListener(mTurnNotificationListener);

        mNotificationsRecyclerView = view.findViewById(R.id.notifications_recyclerView);
        mAdapter = new RecycleNotificationAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mNotificationsRecyclerView.setLayoutManager(layoutManager);
        mNotificationsRecyclerView.setAdapter(mAdapter);
        mNotificationsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mNotificationsRecyclerView.addOnItemTouchListener(new RecyclerNotificationsTouchListener(getContext(),
                mNotificationsRecyclerView));


        showNotificationsAreWorks(mPresenter.notificationsAreWorks());

        showBottomNavigation();
        return view;
    }

    public void updateNotifications(List<Notification> notifications){
        mAdapter.setNotifications(notifications);
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
            mTurnNotificationButton.setText("Включить уведомления");
        }else{
            mTurnNotificationButton.setText("Выключить уведомления");
        }
    }


    //todo по сути копипаст
    class RecyclerNotificationsTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;

        RecyclerNotificationsTouchListener(Context context, final RecyclerView recycleView ){

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
            log("RecyclerNotificationsTouchListener onInterceptTouchEvent()");
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && gestureDetector.onTouchEvent(e)){
                int position = rv.getChildAdapterPosition(child);
                mPresenter.clickedSelectedNotification(position);
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            log("RecyclerNotificationsTouchListener onTouchEvent()");
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            log("RecyclerNotificationsTouchListener onRequestDisallowInterceptTouchEvent" +
                    "(disallowIntercept=%s)" ,disallowIntercept);

        }
    }
}
