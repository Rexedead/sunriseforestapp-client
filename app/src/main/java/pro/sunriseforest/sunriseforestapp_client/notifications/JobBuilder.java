package pro.sunriseforest.sunriseforestapp_client.notifications;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;

public class JobBuilder {

    public static final String ACTION_KEY = "pro.sunriseforest.sunriseforestapp_client.notifications_action_key";

    public static final int ACTION_START_JOB = 1001;


    public static final int NOTIFICATION_JOB_ID = 101;


    private Context mContext;

    public JobBuilder(Context context) {

        mContext = context;

    }

    private JobInfo.Builder getNotificationsJobBuilder(){

        ComponentName jobService = new ComponentName(mContext  , NotificationsJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(NOTIFICATION_JOB_ID, jobService);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(6000);

        } else {
            builder.setPeriodic(6000);
        }
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        builder.setPersisted(true);
        return builder;
    }


    public JobInfo.Builder getNotificationsJobBuilder(int type){
        PersistableBundle bundle = new PersistableBundle();
        bundle.putInt(ACTION_KEY, type);

        return getNotificationsJobBuilder().setExtras(bundle);
    }

}
