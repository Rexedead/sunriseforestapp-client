package pro.sunriseforest.sunriseforestapp_client.ui.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Notification;

public class RecycleNotificationAdapter extends RecyclerView.Adapter<RecycleNotificationAdapter.NotificationsViewHolder>{


    private List<Notification> mNotifications;

    public RecycleNotificationAdapter() {
        mNotifications = new ArrayList<>();

        //todo hs
//        notifyItemRangeChanged(0, mNotifications.size());
    }

    public void setNotifications(List<Notification> notifications) {
        mNotifications = notifications;
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
        Notification notification = mNotifications.get(i);
        notificationsViewHolder.mHeadline.setText(notification.getHeadline());
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    class NotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView mHeadline;

        NotificationsViewHolder(@NonNull View view) {
            super(view);
            mHeadline = view.findViewById(R.id.headline_notification_TextView);
        }

    }
}
