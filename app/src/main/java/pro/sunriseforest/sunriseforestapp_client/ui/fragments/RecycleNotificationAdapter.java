package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;

public class RecycleNotificationAdapter extends RecyclerView.Adapter<RecycleNotificationAdapter.NotificationsViewHolder>{


    private List<SunriseNotification> mSunriseNotifications;
    private NotificationClickListener mListener;

    public RecycleNotificationAdapter(NotificationClickListener listener) {
        mListener = listener;
        mSunriseNotifications = new ArrayList<>();

    }



    public void addSunriseNotifications(List<SunriseNotification> sunriseNotifications, boolean refresh) {

        if(refresh){
            mSunriseNotifications.clear();
        }
        mSunriseNotifications.addAll(sunriseNotifications);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_single_row, viewGroup, false);
        return new NotificationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder notificationsViewHolder, int i) {
        SunriseNotification sunriseNotification = mSunriseNotifications.get(i);

        //todo костыль: исправить, когда сообщения будут готовиться на сервере
        String text = "появились новые таски, целых " + sunriseNotification.getData() + " штук))))";

        notificationsViewHolder.mHeadline.setText(text);
        notificationsViewHolder.setListener(mListener);
        notificationsViewHolder.setPosition(i);
    }

    @Override
    public int getItemCount() {
        return mSunriseNotifications.size();
    }

    class NotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView mHeadline;

        private NotificationClickListener mListener;
        private int mPosition;

        public void setListener(NotificationClickListener listener) {
            mListener = listener;
        }

        public void setPosition(int position) {
            mPosition = position;
        }

        NotificationsViewHolder(@NonNull View view) {
            super(view);
            mHeadline = view.findViewById(R.id.headline_notification_TextView);
            view.setOnClickListener(v -> mListener.onClick(mPosition));
        }


    }

    public interface NotificationClickListener{
        void onClick(int position);
    }
}
