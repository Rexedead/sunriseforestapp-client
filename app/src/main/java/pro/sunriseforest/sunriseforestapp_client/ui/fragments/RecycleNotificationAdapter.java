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

    public RecycleNotificationAdapter() {
        mSunriseNotifications = new ArrayList<>();

        //todo hs
//        notifyItemRangeChanged(0, mSunriseNotifications.size());
    }

    public void setSunriseNotifications(List<SunriseNotification> sunriseNotifications) {
        mSunriseNotifications = sunriseNotifications;
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
        notificationsViewHolder.mHeadline.setText(sunriseNotification.getHeadline());
    }

    @Override
    public int getItemCount() {
        return mSunriseNotifications.size();
    }

    class NotificationsViewHolder extends RecyclerView.ViewHolder {
        TextView mHeadline;

        NotificationsViewHolder(@NonNull View view) {
            super(view);
            mHeadline = view.findViewById(R.id.headline_notification_TextView);
        }

    }
}
